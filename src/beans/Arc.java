package beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Arc implements Comparable<Arc>,PropertyChangeListener{
	
    private int id;
    private Sommet source;
    private Sommet destination;
    private int poids;
    
    PropertyChangeSupport changeSupport;
  
    public Arc(int id, Sommet source, Sommet destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.poids = weight;
        
        changeSupport = new PropertyChangeSupport(this);
    }

    public int getId() {
        return id;
    }
    public Sommet getDestination() {
        return destination;
    }

    public Sommet getSource() {
        return source;
    }
    public int getPoids() {
        return poids;
    }

    public void setId(int id) {
    	int ancienId = this.id;
        this.id = id;
     	int nouveauId = id;
     	changeSupport.firePropertyChange("id",ancienId, nouveauId);
	}

	public void setSource(Sommet source) {
		this.source = source;
		source.addPropertyChangeListner(this);
	}

	public void setDestination(Sommet destination) {
		this.destination = destination;
		destination.addPropertyChangeListner(this);
	}

	public void setPoids(int weight) {
		this.poids = weight;
	}

	@Override
    public String toString() {
        return source + " " + destination;
    }

	@Override
	public int compareTo(Arc edgeAcomparer) {
		
		return this.poids - edgeAcomparer.getPoids();
	}
	
	public int getIdSource() {
		return source.getId();
	}
	public int getIdDestination() {
		return destination.getId();
	}
	
	public String getNomSource() {
		return source.getName();
	}
	public String getNomDestination() {
		return destination.getName();
	}

	public void addPropertyChangeListner(PropertyChangeListener listner) {
	    	changeSupport.addPropertyChangeListener(listner);
	}
	    
	public void removePropertyChangeListner(PropertyChangeListener listner) {
	    	changeSupport.removePropertyChangeListener(listner);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		
	}

}
