package beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Sommet implements PropertyChangeListener{
    private int id;
    private String name;
    
    PropertyChangeSupport changeSupport;


    public Sommet(int id, String name) {
        this.id = id;
        this.name = name;
        
        changeSupport = new PropertyChangeSupport(this);
    }
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
    	
     	int ancienId = this.id;
        this.id = id;
     	int nouveauId = id;
     	changeSupport.firePropertyChange("id",ancienId, nouveauId);
    }

    public String getName() {
        return name;
    }
    
    public void setName(String nom) {
    	String ancienNom = this.name;
        this.name = nom;
        String nouveauNom = nom;
        changeSupport.firePropertyChange("nom",ancienNom, nouveauNom);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == 0) ? 0 : Integer.toString(id).hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sommet other = (Sommet) obj;
        if (id == 0) {
            if (other.id != 0)
                return false;
        } else if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
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
