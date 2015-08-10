/*
 * Copyright 2015 Trento Rise.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.trentorise.opendata.commons.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.junit.Assert;
import org.junit.Test;

class MyInvocationHandler implements InvocationHandler{


    @Override
    public Object invoke(Object o, Method method, Object[] os) throws Throwable {
        if (method.getName().startsWith("set")){
            System.out.println("This is a setter!");
        }
        return null;
    }
}

class MyClass {
    private String name;

    public MyClass() {
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}

/**
 *
 * @author David Leoni
 */
public class JavaProxyTest {

    /**
     * We can use java proxies only with interfaces... sic 
     */
    @Test    
    public void testProxy() {
        InvocationHandler handler = new MyInvocationHandler();
        try {
        MyClass proxy = (MyClass) Proxy.newProxyInstance(
                MyClass.class.getClassLoader(),
                new Class[]{MyClass.class},
                handler);        
        proxy.setName("hugo");
        Assert.fail();
        
        } catch (Exception ex){
            
        }
        
    }
}
