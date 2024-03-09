import greenfoot.*;
/**
 * <p>This is a scuffed, unnecessary solution to a simple problem. If I want something to run after a set
 * amount of time, I can simply use a counting variable in whatever Actor I want. But I thought I could do more and
 * make this work, as each Event can do something unique. I don't need to create multiple Event classes or define 
 * multiple functions to handle different things, as all I need to do is pass in a different function. This <i>does</i> work,
 * for <i>now</i>, but most likely there are many, many edge cases where this would crash and burn.</p>
 * 
 * <p>And seeing that there was only <a href="https://www.greenfoot.org/topics/64859/0">one relevant post</a>
 *  and a few other related ones on the Greenfoot Forums regarding this, there's probably a reason for that...</p>
 * 
 * <hr>
 * 
 * <p>Event is abstract. It will should a given Runnable (function) when a condition is satisfied.
 * Events should take advantage of the fact that it is an Actor, meaning that it has and act() function.
 * The act() function is meant to manage the logic on when an Event should trigger.
 * I have tried to have Events be managed seperately in the World, but it's stuff that's way too beyond my
 * capabilities, so this compromise should be good enough. Each Event should have some sort of action tied
 * to it. That action is a function which is run when the Event is triggered, and is passed in as a parameter.
 * Obviously, a lot of this comes from Google and researching, and is not completely mine.<p>
 * 
 * <p>This is <strong>not</strong> meant to be confused with World Events or Effects.
 * 
 * 
 * @author Freeman Wang
 * @version 2024-02-27
 */
public abstract class Event extends Actor 
{
    protected int act; // The number of acts this has existed.
    protected Runnable event; // What to do when triggered.
    /**
     * Create an Event.
     * @param Action The action to run when the Event is triggered.
     */
    public Event(Runnable action) {
        act = 0; // Current acts of existence is 0.
        event = action; // Set the event to run as the given action.
    }
    public abstract Event copy();
}
