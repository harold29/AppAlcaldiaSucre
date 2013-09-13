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

import java.util.Hashtable;
import net.rim.device.api.system.Application;

/**
 * An abstract class that implements a generic asynchronous request. It makes no
 * assumption of the request type, if network or not. It basically allows any
 * client to asynchronously request and receive notification when it has been
 * completed with a status.
 *
 * @author kseo
 */
public abstract class BBRequest
{
	protected Thread worker = null;
	protected int requestId = 0;
	protected Listener listener = null;
	protected BBRequestStatus status = BBRequestStatus.INITIALIZED;
	protected Throwable error = null;
	protected long requestTimeout = 0L;
	protected Hashtable enclosed = new Hashtable();
	protected boolean priority = false;
	protected boolean cancellable = true;
	protected Application caller;

	public interface Listener
	{
		public void requestStarted(BBRequest request);

		public void requestSucceeded(BBRequest request);

		public void requestCancelled(BBRequest request);

		public void requestFailed(BBRequest request);
	}

	public void setListener(Listener listener)
	{
		this.listener = listener;
	}

	public Listener getListener()
	{
		return this.listener;
	}

	private void notifyListener()
	{
		if (listener != null && status != null)
		{
			try
			{
				if (status.equals(BBRequestStatus.STARTED))
					listener.requestStarted(this);
				else if (status.equals(BBRequestStatus.SUCCEEDED))
					listener.requestSucceeded(this);
				else if (status.equals(BBRequestStatus.CANCELLED))
					listener.requestCancelled(this);
				else if (status.equals(BBRequestStatus.FAILED))
					listener.requestFailed(this);
			}
			catch (Throwable t)
			{
				this.error = t;
				System.out
						.println("Exception in Callback, review the callback handling ["
								+ t.getClass().getName()
								+ "] "
								+ t.getMessage());
			}
		}
	}

	protected void notifyRequestStarted()
	{
		synchronized (status)
		{
			this.setRequestStatus(BBRequestStatus.STARTED);
			notifyListener();
		}
	}

	protected void notifyRequestSucceeded()
	{
		synchronized (status)
		{
			if (wasCancelled()) return;
			this.setRequestStatus(BBRequestStatus.SUCCEEDED);
			notifyListener();
		}
	}

	protected void notifyRequestCancelled()
	{
		synchronized (status)
		{
			this.setRequestStatus(BBRequestStatus.CANCELLED);
			notifyListener();
		}
	}

	protected void notifyRequestFailed()
	{
		synchronized (status)
		{
			this.setRequestStatus(BBRequestStatus.FAILED);
			notifyListener();
		}
	}

	public int getRequestId()
	{
		return requestId;
	}

	public void setRequestId(int requestId)
	{
		this.requestId = requestId;
	}

	public String getErrorMessage()
	{
		String msg = null;
		Throwable t = getError();
		if (t != null) msg = t.getMessage();
		return msg;
	}

	public Throwable getError()
	{
		return error;
	}

	public void setError(Throwable error)
	{
		this.error = error;
	}

	public synchronized BBRequestStatus getRequestStatus()
	{
		return status;
	}

	public synchronized void setRequestStatus(BBRequestStatus status)
	{
		this.status = status;
	}

	public synchronized boolean wasSuccessful()
	{
		return status.equals(BBRequestStatus.SUCCEEDED);
	}

	public synchronized boolean wasCancelled()
	{
		return status.equals(BBRequestStatus.CANCELLED);
	}

	public void addAttachment(Object key, Object value)
	{
		enclosed.put(key, value);
	}

	public Object getAttachment(Object key)
	{
		return enclosed.get(key);
	}

	public void clearAttachments()
	{
		enclosed.clear();
	}

	public long getRequestTimeout()
	{
		return requestTimeout;
	}

	public void setRequestTimeout(long requestTimeout)
	{
		this.requestTimeout = requestTimeout;
	}

	protected void cancel()
	{
		// cancel behaviour must be defined by subclass
	}

	// only the queue can cancel request
	final boolean doCancel(boolean force)
	{
		if (status.equals(BBRequestStatus.SUCCEEDED)
				|| status.equals(BBRequestStatus.FAILED)) return false;

		if (force || isCancellable())
		{
			notifyRequestCancelled();
			try
			{
				cancel();
			}
			catch (Exception e)
			{
				handleCancellationError(e);
			}
			catch (Throwable t)
			{
				handleCancellationError(t);
			}
			finally
			{
				try
				{
					synchronized (this)
					{
						if (worker != null)
						{
							worker.interrupt();
							worker = null;
						}
					}
				}
				catch (Exception e)
				{
					handleCancellationError(e);
				}
				catch (Throwable t)
				{
					handleCancellationError(t);
				}
			}
			return true;
		}
		return false;
	}

	// worker thread
	public synchronized Thread getWorker()
	{
		return worker;
	}

	public synchronized void setWorker(Thread worker)
	{
		this.worker = worker;
	}

	boolean isProcessing()
	{
		return (getWorker() != null);
	}

	public boolean isPriority()
	{
		return priority;
	}

	public void setPriority(boolean priority)
	{
		this.priority = priority;
	}

	public boolean isCancellable()
	{
		return cancellable;
	}

	public synchronized void setCaller(Application caller)
	{
		this.caller = caller;
	}

	public synchronized Application getCaller()
	{
		return caller;
	}

	public void setCancellable(boolean cancellable)
	{
		this.cancellable = cancellable;
	}

	public String toString()
	{
		return getClass().getName() + " [ID: " + requestId + "]";
	}

	protected void preProcess() throws Throwable
	{
		// do nothing by default
	}

	protected void postProcess() throws Throwable
	{
		// do nothing by default
	}

	// process the request, set status, notify listeners, set error messages
	// when needed
	protected abstract void process() throws Throwable;

	// package protected
	final void doProcess()
	{
		try
		{
			// notify request started if any listener is registered
			this.notifyRequestStarted();
			// pre process
			this.preProcess();
			// main process
			this.process();
			// post process
			this.postProcess();
			// notify request succeeded if any listener is registered
			this.notifyRequestSucceeded();
		}
		catch (Exception e)
		{
			handleError(e);
		}
		catch (Throwable t)
		{
			handleError(t);
		}
	}

	private void handleError(Throwable t)
	{
		if (!wasCancelled())
		{
			this.setError(t);
			System.out.println("BBRequest Error: [" + t.getClass().getName()
					+ "] " + t.getMessage());
			// notify request failed
			
			this.notifyRequestFailed();
		}
	}

	private void handleCancellationError(Throwable t)
	{
		// we basically ignore
		System.out
				.println("Exception in Cancelling request, please review the cancel implementation ["
						+ t.getClass().getName() + "] " + t.getMessage());
	}

}
