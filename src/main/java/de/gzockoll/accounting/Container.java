package de.gzockoll.accounting;

public class Container<E> {

	    public static <E> Container<E> create(Class<E> c) {
	    	return new Container<E>(c);
	    }

	    Class<E> c;

	    public Container(Class<E> c) {
	    	super();
	    	this.c = c;
	    }

	    public E createInstance()
	    		throws InstantiationException,
	    		IllegalAccessException {
	    	return c.newInstance();
	    }

	}
