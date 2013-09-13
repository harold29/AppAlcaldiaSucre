/**
 * Copyright © 1998-2009 Research In Motion Ltd.
 *
 * Note:
 *
 * 1. For the sake of simplicity, this sample application may not leverage
 * resource bundles and resource strings.  However, it is STRONGLY recommended
 * that application developers make use of the localization features available
 * within the BlackBerry development platform to ensure a seamless application
 * experience across a variety of languages and geographies.  For more information
 * on localizing your application, please refer to the BlackBerry Java Development
 * Environment Development Guide associated with this release.
 *
 * 2. The sample serves as a demonstration of principles and is not intended for a
 * full featured application. It makes no guarantees for completeness and is left to
 * the user to use it as sample ONLY.
 */
package com.mobilemedia.AppAlcaldiaSucre.transport;

import java.util.Vector;
import net.rim.device.api.system.Application;

/**
 * A multi-threaded request handling queue, see {@link BBRequest} for more
 * details
 *
 * @author kseo
 */
public class BBRequestQueue
{
	private static final int DEFAULT_MAX_THREAD_COUNT = 3;
	private Vector pendingRequests = new Vector(5);
	private WorkerThreadGroup workers = null;
	private static BBRequestQueue queue = null;

	private static Object mutex = new Object();

	/**
	 * Singleton class where that returns the instance of the current queue
	 *
	 * @return
	 */
	public static BBRequestQueue getInstance()
	{
		if (queue == null)
		{
			synchronized (mutex)
			{
				int maxThreadCount = DEFAULT_MAX_THREAD_COUNT;
				if (maxThreadCount < 1 || maxThreadCount > 5)
					maxThreadCount = DEFAULT_MAX_THREAD_COUNT;

				Object registered = null;
				if (registered == null)
				{
					// should only happen once
					queue = new BBRequestQueue(maxThreadCount);
				}
				else if (registered instanceof BBRequestQueue)
				{
					// this will be the case that getInstance() is invoked from
					// some other process. (like notification)
					queue = (BBRequestQueue) registered;
				}
			}
			/*
			 * Else it means something else is registered using the same id..
			 * Should not happen ..
			 */
		}
		return queue;
	}

	protected BBRequestQueue()
	{
		this(DEFAULT_MAX_THREAD_COUNT);
	}

	protected BBRequestQueue(int maxThreadCount)
	{
		workers = new WorkerThreadGroup(maxThreadCount);
	}

	public void addRequest(BBRequest request)
	{
		Application caller = Application.getApplication();
		request.setCaller(caller);
		// Queue the request and process it on the request thread.
		synchronized (pendingRequests)
		{
			if (request.isPriority())
			{
				pendingRequests.insertElementAt(request, 0);
			}
			else
			{
				pendingRequests.addElement(request);
			}

			System.out.println("Added BBRequest, Queue["
					+ pendingRequests.size() + "]: " + request);
			workers.createThread();
		}
	}

	private void removeRequest(BBRequest request)
	{
		synchronized (pendingRequests)
		{
			pendingRequests.removeElement(request);
			System.out.println("Removed BBRequest, Queue["
					+ pendingRequests.size() + "]: " + request);
		}
	}

	public void cancelRequest(BBRequest request, boolean force)
	{
		boolean cancelled = request.doCancel(force);
		if (cancelled) removeRequest(request);
	}

	public void cancelRequest(BBRequest request)
	{
		cancelRequest(request, false);
	}

	/**
	 * Cancell all requests and clean up.
	 *
	 * @param force - if true then forces cancellation of the current
	 * worker(s) thats handling the request if and only if the request
	 * is cancellable. {@link BBRequest - isCancellable()}
	 */
	public void cancelAllRequests(boolean force)
	{
		System.out.println("Attempting to cancel all BBRequests");
		Vector cancelledRequests = new Vector(5);
		synchronized (pendingRequests)
		{
			for (int i = 0; i < pendingRequests.size(); i++)
			{
				BBRequest request = (BBRequest) pendingRequests.elementAt(i);
				boolean cancelled = request.doCancel(force);
				if (cancelled) cancelledRequests.addElement(request);
			}
		}

		for (int i = 0; i < cancelledRequests.size(); i++)
		{
			BBRequest request = (BBRequest) cancelledRequests.elementAt(i);
			removeRequest(request);
			Thread t = request.getWorker();
			if (t != null && t.isAlive()) workers.killWorker(t);
		}
		cancelledRequests.removeAllElements();
		cancelledRequests = null;
		System.out.println("All BBRequests are cancelled, Queue["
				+ pendingRequests.size() + "]");
	}

	/**
	 * Cancells all outstanding requests. However,
	 */
	public void cancelAllRequests()
	{
		cancelAllRequests(false);
	}

	/**
	 * On abort and shutdown transport queue and all operations.
	 */
	public void shutdown()
	{
		cancelAllRequests(true);
		// the following operations are not required, but just to make sure
		// everything is cleaned up.
		pendingRequests.removeAllElements();
		workers.killAll();
	}

	public int getRequestCount()
	{
		synchronized (pendingRequests)
		{
			return pendingRequests.size();
		}
	}

	private class WorkerThread extends Thread
	{
		private long requestProcessingStart = 0L;

		private long requestTimeout = 0L;

		private boolean interrupted = false;

		public WorkerThread(String name)
		{
			super(name);
		}

		public void interrupt()
		{
			interrupted = true;
			try
			{
				if (this.isAlive())
				{
					super.interrupt();
				}
			}
			catch (Exception e)
			{
				// too bad
			}
			catch (Throwable t)
			{
				// just handle, but cant realy do much here
			}
			finally
			{
				System.out.println("Interrupted Thread: " + this.getName());
			}
		}

		public boolean isTimeout()
		{
			long now = System.currentTimeMillis();
			return (requestTimeout > 0 && requestProcessingStart > 0 && (now - requestProcessingStart) >= requestTimeout);
		}

		private synchronized void setTimebomb(long start, long timeout)
		{
			this.requestProcessingStart = start;
			this.requestTimeout = timeout;
		}

		public void run()
		{
			try
			{
				System.out.println("Starting Thread: " + this.getName());
				while (!interrupted) // if interruped, chances are it's in a
										// currupt state, so we discard.
				{
					BBRequest request = null;
					synchronized (pendingRequests)
					{
						// check if any pending request is waiting
						boolean newRequest = false;
						for (int i = 0; i < pendingRequests.size(); i++)
						{
							request = (BBRequest) pendingRequests.elementAt(i);
							if (!request.isProcessing())
							{
								request.setWorker(this);
								newRequest = true;
								System.out.println("Thread: " + this.getName()
										+ " handling request: " + request);
								break;
							}
						}
						if (!newRequest) break;
					}
					try
					{
						setTimebomb(System.currentTimeMillis(), request
								.getRequestTimeout());
						request.doProcess();
					}
					catch (Exception e)
					{
						handleError(e);
					}
					catch (Throwable t)
					{
						handleError(t);
					}
					finally
					{
						// even when interrupted, this will be invoked.
						removeRequest(request);
					}
				}
			}
			finally
			{
				// even when interrupted, this will be invoked.
				System.out.println("Stopping Thread: " + this.getName());
			}
		}

		private void handleError(Throwable t)
		{
			System.out
					.println("BBRequest, catching something that shouldnt have happened: ["
							+ t.getClass().getName() + "] " + t.getMessage());
		}

	}

	/**
	 * Define a ThreadGroup, its much easier to collectively deal with the workers.
	 * @author Ken Seo
	 */
	private class WorkerThreadGroup
	{
		private static final String namePrefix = "Worker";
		private Vector threads = null;
		private long threadId = 0L;

		private int maxCount = 1;

		public WorkerThreadGroup(int maxCount)
		{
			threads = new Vector(maxCount);
			this.maxCount = maxCount;
		}

		private void killThread(Thread thread)
		{
			try
			{
				thread.interrupt();
				thread = null;
			}
			catch (Throwable t)
			{
			}
		}

		public boolean createThread()
		{
			boolean created = false;
			synchronized (threads)
			{
				int size = getWorkerThreadCount();
				if (size < maxCount)
				{
					Thread t = new WorkerThread(namePrefix + threadId++);
					t.start();
					threads.addElement(t);
					created = true;
				}
			}
			System.out.println("Max thread count: " + maxCount
					+ ", Active thread count: " + threads.size());
			return created;
		}

		public void killAll()
		{
			synchronized (threads)
			{
				for (int i = 0; i < threads.size(); ++i)
				{
					Thread t = (Thread) threads.elementAt(i);
					killThread(t);
				}
				threads.removeAllElements();
			}

			System.out.println("Killed all active threads - size: "
					+ threads.size());
		}

		public void killWorker(Thread t)
		{
			synchronized (threads)
			{
				threads.removeElement(t);
				killThread(t);
				System.out.println("Killed thread [cancelled]: " + t.getName());
			}
		}

		public int getWorkerThreadCount()
		{
			refresh();
			return threads.size();
		}

		public void refresh()
		{
			synchronized (threads)
			{
				for (int i = 0; i < threads.size();)
				{
					WorkerThread t = (WorkerThread) threads.elementAt(i);
					if (!t.isAlive())
					{
						threads.removeElementAt(i);
					}
					else if (t.isTimeout())
					{
						threads.removeElementAt(i);
						killThread(t);
						System.out.println("Killed thread [timeout]: "
								+ t.getName());
					}
					else
					{
						++i;
					}
				}
			}
		}
	}
}
