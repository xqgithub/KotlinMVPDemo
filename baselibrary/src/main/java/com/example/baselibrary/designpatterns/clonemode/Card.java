package com.example.baselibrary.designpatterns.clonemode;

/**
 * 原型模式-原型类
 */
public class Card implements Cloneable {//实现Cloneable接口，Cloneable只是标识接口
    private int num;//卡号

    private Spec spec = new Spec();//卡规格

    public Card() {
        System.out.println("Card 执行构造函数");
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setSpec(int length, int width) {
        spec.setLength(length);
        spec.setWidth(width);
    }

    @Override
    public String toString() {
        return "Card{" +
                "num=" + num +
                ", spec=" + spec +
                '}';
    }

    @Override
    public Card clone() throws CloneNotSupportedException {//重写clone()方法，clone()方法不是Cloneable接口里面的，而是Object里面的
        System.out.println("clone时不执行构造函数");
        return (Card) super.clone();
    }
}

//规格类，有长和宽这两个属性
class Spec {
    private int width;
    private int length;

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Spec{" +
                "width=" + width +
                ", length=" + length +
                '}';
    }
}
