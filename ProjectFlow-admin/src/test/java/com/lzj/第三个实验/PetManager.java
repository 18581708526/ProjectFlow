package com.lzj.第三个实验;

import java.util.ArrayList;
import java.util.List;

public class PetManager {
    private List<Pet> pets=new ArrayList<Pet>();

    //添加宠物方法
    public List<Pet> addPet(Pet p) {
        pets.add(p);
        return pets;
    }
    public String quaryPetforName(List<Pet> pets,String name){
        ArrayList<Pet> pets1 = new ArrayList<>();
        for(Pet p:pets){
            if(name.equals(p.getName())){
                pets1.add(p);
            }
        }
        if(pets1.size() == 0){
            return "没有找到名字为”"+name+"“的宠物";
        }
        return "名称为"+name+"的宠物合集："+pets1.toString();
    }

    @Override
    public String toString() {
        return "PetManager{" +
                "宠物合集=" + pets +
                '}';
    }
}
