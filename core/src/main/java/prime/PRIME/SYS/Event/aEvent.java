package prime.PRIME.SYS.Event;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Pool.Poolable;

import prime.PRIME.N_M._N.Node;
import prime.PRIME.SYS._Environment;

public class aEvent implements iSignal, Poolable {

	private _Environment environment;
	private Node target;
	private Node listener; //sender
	
	private boolean capture; // true means event occurred during the capture phase
	private boolean bubbles = true; // true means propagate to target's parents
	private boolean handled; // true means the event was handled (the stage will eat the input)
	private boolean stopped; // true means event propagation was stopped
	private boolean cancelled; // true means propagation was stopped and any action that this event would cause
								// should not happen


	
	/** Marks this event as handled. This does not affect event propagation inside scene2d, but causes the {@link Stage}
	 * {@link InputProcessor} methods to return true, which will eat the event so it is not passed on to the application under the
	 * stage. */
	public void handle () {
		handled = true;
	}

	/** Marks this event cancelled. This {@link #handle() handles} the event and {@link #stop() stops} the event propagation. It
	 * also cancels any default action that would have been taken by the code that fired the event. Eg, if the event is for a
	 * checkbox being checked, cancelling the event could uncheck the checkbox. */
	public void cancel () {
		cancelled = true;
		stopped = true;
		handled = true;
	}

	/** Marks this event has being stopped. This halts event propagation. Any other listeners on the {@link #getListenerActor()
	 * listener actor} are notified, but after that no other listeners are notified. */
	public void stop () {
		stopped = true;
	}

	public void reset () {
		environment = null;
		target = null;
		listener = null;
		capture = false;
		bubbles = true;
		handled = false;
		stopped = false;
		cancelled = false;
	}

	/** Returns the actor that the event originated from. */
	public Node getTarget () {
		return target;
	}

	public void setTarget (Node targetActor) {
		this.target = targetActor;
	}

	/** Returns the actor that this listener is attached to. */
	public Node getListenerActor () {
		return listener;
	}

	public void setListenerActor (Node listenerActor) {
		this.listener= listenerActor;
	}

	public boolean getBubbles () {
		return bubbles;
	}

	/** If true, after the event is fired on the target actor, it will also be fired on each of the parent actors, all the way to
	 * the root. */
	public void setBubbles (boolean bubbles) {
		this.bubbles = bubbles;
	}

	/** {@link #handle()} */
	public boolean isHandled () {
		return handled;
	}

	/** @see #stop() */
	public boolean isStopped () {
		return stopped;
	}

	/** @see #cancel() */
	public boolean isCancelled () {
		return cancelled;
	}

	public void setCapture (boolean capture) {
		this.capture = capture;
	}

	/** If true, the event was fired during the capture phase.
	 * @see Actor#fire(Event) */
	public boolean isCapture () {
		return capture;
	}
	
}
