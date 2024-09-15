package HttpServer.JSON;

import java.util.List;

public class TestPOJO {
    private String helloWorld;

    private Integer num;

    private List<String> myList;

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

    public List<String> getMyList() {
        return myList;
    }

    public void setMyList(List<String> myList) {
        this.myList = myList;
    }
}
