package com.lzj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 练习java构造者模式
 * 1.支持链式调用
 */
public class Product {
    private Integer product_id;
    private String product_name;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date create_time;
    private boolean isused;

    public Product(Builder builder) {
        this.product_id = builder.product_id;
        this.product_name = builder.product_name;
        this.create_time = builder.create_time;
        this.isused = builder.isused;
    }
    public static class Builder {
        private Integer product_id;
        private String product_name;
        @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone = "GMT+8")
        private Date create_time;
        private boolean isused;

        public Builder setProduct_id(Integer product_id) {
            this.product_id = product_id;
            return this;
        }

        public Builder setProduct_name(String product_name) {
            this.product_name = product_name;
            return this;
        }

        public Builder setCreate_time(Date create_time) {
            this.create_time = create_time;
            return this;
        }

        public Builder setIsused(boolean isused) {
            this.isused = isused;
            return this;
        }

        public Product build(){
            return new Product(this);
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return "Product{" +
                "product_id=" + product_id +
                ", product_name='" + product_name  +
                ", create_time=" + format.format(create_time) +
                ", isused=" + isused +
                '}';
    }
}

