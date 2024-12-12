package HttpServer.JSON;

import java.util.List;

public class TestPOJO {
    private String helloWorld;

    private Integer num;

    private List<Double> myList;

    private OtherTest test;

    public TestPOJO(){

    }

    public String getHelloWorld() {
        return helloWorld;
    }

    public void setHelloWorld(String helloWorld) {
        this.helloWorld = helloWorld;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<Double> getMyList() {
        return myList;
    }

    public void setMyList(List<Double> myList) {
        this.myList = myList;
    }

    public OtherTest getTest() {
        return test;
    }

    public void setTest(OtherTest test) {
        this.test = test;
    }
}
