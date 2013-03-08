package com.ykoer.dbforms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ykoer.dbforms.model.FormField;
import com.ykoer.dbforms.model.FormField.DataType;
import com.ykoer.dbforms.model.FormField.FieldType;
import com.ykoer.dbforms.orm.Query;

@Stateless
@LocalBean
public class QueryManager {
    
    @EJB
    DatabaseDAO dao;
    
    public List<FormField> getFormData(long queryId) {
        
        Query query = dao.getQuery(queryId);
        System.out.println("Query Id: " + queryId + ", " + query.getName());
        
        
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
            "and    hp.fooo     LIKE    %s " +
            "and    hp.fooo   in   (%s) " +
            "and    ps.location_id = hl.location_id " +
            "and    ps.party_site_id = casa.party_site_id " +
            "and    casa.cust_acct_site_id      =       csua.cust_acct_site_id(+) " +
            "order by ps.party_site_number";
        
        
        // clean unnecessary spaces
        input = input.replaceAll("\\s+=\\s+", "=");
        input = input.replaceAll("(?i)\\s+like\\s+", " like ");
        input = input.replaceAll("(?i)\\s+in\\s+", " in ");
        
        Pattern p = Pattern.compile("(\\S+)(=|\\slike\\s|\\sin\\s)(\\S+)");
        Matcher m = p.matcher(input);
        
        List<FormField> fieldList = new ArrayList<FormField>();
        StringBuffer result = new StringBuffer();
        while (m.find()) {
            
            if (m.group().contains("%")) {
                System.out.println("Mask: " + m.group(1) + "\t" + m.group(2) + "\t" + m.group(3) + "  -->  " + m.group());

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
                
                // no form metadata found, use defaults 
                field.setLabel(operand1);
                field.setFieldName(operand1);
                field.setFieldType(FieldType.TEXT);
                field.setDataType(DataType.STRING);
                 
                fieldList.add(field);
                System.out.println("Field Size: " + field.getOptions().size());
            }
        }
        return fieldList;
    }

}
