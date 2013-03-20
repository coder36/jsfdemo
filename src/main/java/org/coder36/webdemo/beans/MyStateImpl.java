package org.coder36.webdemo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@ManagedBean( name="myStateBean")
@SessionScoped
public class MyStateImpl implements MyState, Serializable {
	
	private List<DataContainer> data = new ArrayList<DataContainer>();	
	
	public List<DataContainer> getData() {
		return data;
	}
	
			
}
