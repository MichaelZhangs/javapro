package com.mywork.loan.final_project.test;

import com.mywork.loan.entity.loanAmount;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class TestDrools {

    @Test
    public void test(){
        // 第一步：
        KieServices kieServices = KieServices.Factory.get();

        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();

        KieSession kieSession = kieClasspathContainer.newKieSession();

        loanAmount amount = new loanAmount();
        amount.setAge(27);
        amount.setEdu("大学");


        kieSession.insert(amount);
        kieSession.fireAllRules();

        kieSession.dispose();
        System.out.println("贷款金额是 : " + amount.getAmount() + amount.getInfo());

    }


}
