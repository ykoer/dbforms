package com.ykoer.dbforms.model;

import java.util.HashMap;


public class FormField {
    
    private String fieldName;
    private String label;
    private FieldType fieldType;
    private DataType dataType;
    private HashMap<String,String> options;
    
    public enum FieldType { TEXT,TEXTAREA,DATE,SELECT,RADIO };
    public enum DataType { NUMBER,STRING,BOOLEAN,DATE }
    
    
    
    public FormField() {
        options = new HashMap<String, String>();
    }

    public String getFieldName() {
        return fieldName;
    }
    
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public FieldType getFieldType() {
        return fieldType;
    }
    
    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }
    
    public DataType getDataType() {
        return dataType;
    }
    
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    
    public HashMap<String,String> getOptions() {
        return options;
    }

    
    public void setOptions(HashMap<String,String> options) {
        this.options = options;
    }
    
    public void addOptions(String key, String value) {
        this.options.put(key, value);
    }
}
