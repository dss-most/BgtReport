package th.go.dss.topbudget.model.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeMaps {
	private List<TreeMaps> children;
	private Integer id;
	private String name;
	private Map<String, Object> data;
	
	public TreeMaps(Integer id, String name) {
		this.id = id;
		this.name = name;
		data = new HashMap<String, Object>();
		children = new ArrayList<TreeMaps>();
	}
	
	public void addChild(TreeMaps treeMaps) {
		if(children == null) {
			children = new ArrayList<TreeMaps>();
		}
		
		children.add(treeMaps);
	}
	
	public void addData(String key, Object value) {
		if(data == null) {
			data = new HashMap<String, Object>();
		}
		
		data.put(key, value);
	}
	
	public List<TreeMaps> getChildren() {
		return children;
	}
	public void setChildren(List<TreeMaps> children) {
		this.children = children;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	
	
}
