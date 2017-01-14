package testdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ParsingHorror {
    private SomeClass stringHolder;
    private Map<Integer, List<String>> someGeneric;

    private abstract class AbstractClass {
        private String someVariable;

        public void doSomething() {
        }

        public String getSomeVariable() {
            return someVariable;
        }

        public void setSomeVariable(String someVariable) {
            this.someVariable = someVariable;
        }
    }

    private interface SomeInterface {
        public default void doSomethingElse(String someParameter) {
        }
    }
    
    private interface UnusedInterface {
        public default void doSomethingElse(String someParameter) {
        }
    }

    private @interface SomeAnnotation {
        String someValue();
    }

    @SomeAnnotation(someValue = "something")
    private class SomeClass extends AbstractClass implements SomeInterface {
        public SomeClass() {
            this.setSomeVariable(null);
        }

        @Override
        public void doSomething() {
            if (this.getSomeVariable() == null) {
                this.setSomeVariable("SomeLiteral");
            }
        }

        @Override
        public void doSomethingElse(String someParameter) {
            this.setSomeVariable(someParameter);
        }
    }

    public ParsingHorror() {
        setStringHolder(new SomeClass());
    }

    public void letUsUseAllControlStatementsInOneMethod() {
        stringHolder.doSomething();
        while (stringHolder.getSomeVariable().length() < 10) {
            stringHolder.doSomethingElse(stringHolder.getSomeVariable() + "1");
        }
        if (stringHolder.getSomeVariable().endsWith("1")) {
            someGeneric.put(1, new ArrayList<String>());
        } else {
            someGeneric.put(0, new ArrayList<String>());
        }
        {
            int i = 0;
            try {
                i = someGeneric.get(1).size();
            } catch (Exception e) {
                i = someGeneric.get(0).size();
            } finally {
                someGeneric.put(i, Arrays.asList("aLiteral"));
            }
            do {
                someGeneric.get(i).add("anotherLiteral");
            } while (someGeneric.get(i).size() < 100);
            for (int j = 0; j < 10; j++)
                someGeneric.put(j, Arrays.asList("firstLiteral", "secondLiteral"));
            for (Integer key : someGeneric.keySet()) {
                for (String literal : someGeneric.get(key)) {
                    switch (literal) {
                    case "firstLiteral":
                    case "secondLiteral":
                        System.out.println(literal);
                        break;
                    case "aLiteral":
                        System.out.println("It's something");
                        continue;
                    default:
                        System.err.println("It's something else");
                        return;
                    }
                }
            }
        }
    }

    public SomeClass getStringHolder() {
        return stringHolder;
    }

    public void setStringHolder(SomeClass stringHolder) {
        this.stringHolder = stringHolder;
    }

    public Map<Integer, List<String>> getSomeGeneric() {
        return someGeneric;
    }

    public void setSomeGeneric(Map<Integer, List<String>> someGeneric) {
        this.someGeneric = someGeneric;
    }
}
