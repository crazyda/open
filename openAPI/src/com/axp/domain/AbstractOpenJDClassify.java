package com.axp.domain;

/**
 * AbstractOpenGoods entity provides the base persistence definition of the
 * OpenGoods entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOpenJDClassify implements java.io.Serializable {
	
	//商品分类
	
	/*"grade": 0,
    "id": 13887,
    "name": "邮币",
    "parentId": 0*/
	
    public Integer id ; 
	public Integer grade ;
	public Integer catId ;
	public String name ;
	public Integer parentId ;
	public Integer classify;
	public String classifyName;
	
	
	
	
	
	
	public AbstractOpenJDClassify() {
		super();
	}
	
	public AbstractOpenJDClassify(Integer id, Integer grade, Integer catId,
			String name, Integer parentId, Integer classify, String classifyName) {
		super();
		this.id = id;
		this.grade = grade;
		this.catId = catId;
		this.name = name;
		this.parentId = parentId;
		this.classify = classify;
		this.classifyName = classifyName;
	}

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getCatId() {
		return catId;
	}
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	
	
}