package com.ykoer.dbforms.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ykoer.dbforms.model.FormField;
import com.ykoer.dbforms.model.FormField.DataType;
import com.ykoer.dbforms.model.FormField.FieldType;


public class Test {
    
    public void buildQuery() {
        String input =
                "Select hp.party_name,hca.account_number " +
                "From   ar.hz_cust_Accounts hca, " +
                "       ar.hz_parties hp, " +
                "       ar.hz_party_sites ps, " +
                "       ar.hz_locations hl, " +
                "       ar.hz_cust_acct_sites_all casa, " +
                "       ar.hz_cust_site_uses_all csua " +
                "Where  hca.account_number = %s " +
                "and    hca.party_id = hp.party_id " +
                "and    hp.party_id = ps.party_id " +
                "and    hp.fooo     LIKE    '%s' " +
                "and    hp.fooo   in   (%s) " +
                "and    ps.location_id = hl.location_id " +
                "and    ps.party_site_id = casa.party_site_id " +
                "and    casa.cust_acct_site_id      =       csua.cust_acct_site_id(+) " +
                "order by ps.party_site_number";
        
        String sql = String.format(input, "113","456","789");
        System.out.println(sql);
    }
 
    
    public List<FormField> getFormData() {
        String input =
            "Select hp.party_name,hca.account_number " +
            "From   ar.hz_cust_Accounts hca, " +
            "       ar.hz_parties hp, " +
            "       ar.hz_party_sites ps, " +
            "       ar.hz_locations hl, " +
            "       ar.hz_cust_acct_sites_all casa, " +
            "       ar.hz_cust_site_uses_all csua " +
            "Where  hca.account_number = %s[Account#,Text,Number] " + //
            "and    hca.party_id = hp.party_id " +
            "and    hp.party_id = ps.party_id " +
            "and    hp.fooo     LIKE    %s " +
            "and    hp.fooo   in   (%s[Foo,Select,Field1,Field2]) " +
            "and    ps.location_id = hl.location_id " +
            "and    ps.party_site_id = casa.party_site_id " +
            "and    casa.cust_acct_site_id      =       csua.cust_acct_site_id(+) " +
            "order by ps.party_site_number";
        
        
       
        input = input.replaceAll("\\s+=\\s+", "=");
        input = input.replaceAll("(?i)\\s+like\\s+", " like ");
        input = input.replaceAll("(?i)\\s+in\\s+", " in ");
        
        //String input3 = "User clientId=23421. Some more text clientId=33432. This clientNum=100";

        //Pattern p = Pattern.compile("(\\S+)=(\\S+)");
        //Pattern p = Pattern.compile("(\\S+)=(\\S+)|(\\S+)\\slike\\s(\\S+)");
        Pattern p = Pattern.compile("(\\S+)(=|\\slike\\s|\\sin\\s)(\\S+)");
        Matcher m = p.matcher(input);
        
        Pattern p1 = Pattern.compile("(?i)\\[(\\S+),(TEXT|TEXTAREA|DATE),(NUMBER|STRING|BOOLEAN)\\]");
        Pattern p2 = Pattern.compile("(?i)\\[(\\S+),(SELECT|RADIO),(\\S+)\\]");
        
        //m.appendReplacement(result, m.group(1) + "***masked***");
        
        List<FormField> fieldList = new ArrayList<FormField>();
        StringBuffer result = new StringBuffer();
        while (m.find()) {
            
            if (m.group().contains("%")) {
                
                System.out.println("Masking: " + m.group(1) + "\t" + m.group(2) + "\t" + m.group(3) + "  -->  " + m.group());
                
                
                String operand1="";
                String operand2="";
                if (m.group(3).contains("%")) {
                    operand1 = m.group(1);
                    operand2 = m.group(3);
                } else {
                    operand1 = m.group(3);
                    operand2 = m.group(1);
                }
                
                FormField field = new FormField();
                
                Matcher m1 = p1.matcher(operand2);
                Matcher m2 = p2.matcher(operand2);
                if (m1.find())  {
                    field.setLabel(m1.group(1));
                    field.setFieldName(operand1);
                    field.setFieldType(FieldType.valueOf(m1.group(2).toUpperCase()));
                    field.setDataType(DataType.valueOf(m1.group(3).toUpperCase()));
                } else if (m2.find())  {
                    field.setLabel(m2.group(1));
                    field.setFieldName(operand1);
                    field.setFieldType(FieldType.valueOf(m2.group(2).toUpperCase()));
                    
                    StringTokenizer t = new StringTokenizer(m2.group(3),",");
                    while (t.hasMoreTokens()) {
                        String option = t.nextToken();
                        field.addOptions(option,option);
                    }
                } else {
                    // no form metadata found, use defaults 
                    field.setLabel(operand1);
                    field.setFieldName(operand1);
                    field.setFieldType(FieldType.TEXT);
                    field.setDataType(DataType.STRING);
                }
                 
                fieldList.add(field);
                System.out.println(field.getOptions().size());
            }
        }
        
        return fieldList;
    }
    
    public static void main(String[] args) {
        
        Test qp = new Test();
        qp.buildQuery();
        
        
        
    }

}
