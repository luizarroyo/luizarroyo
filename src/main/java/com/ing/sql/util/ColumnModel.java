package com.ing.sql.util;

import java.io.Serializable;
import java.util.Arrays;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "ColumnModel")
@ApplicationScoped
public class ColumnModel implements Serializable {

    private String key;
    private Object[] values;

    public ColumnModel(String key, Object[] values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public Object[]  getValues() {
        return values;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + Arrays.deepHashCode(values);
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
		ColumnModel other = (ColumnModel) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (!Arrays.deepEquals(values, other.values))
			return false;
		return true;
	}
    
    
}