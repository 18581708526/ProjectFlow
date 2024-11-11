package com.lzj.第三个实验;

import java.util.List;

public class PetProgram {
    public static void main(String[] args) {
        Pet p1 = new Pet("小花", 3, "狗");
        Pet p2 = new Pet("小白", 3, "猫");
        Pet p3 = new Pet("小黑", 2, "老鼠");
        List<Pet> pets = new PetManager().addPet(p1);
        pets.add(p2);
        pets.add(p3);
        pets.forEach(System.out::println);
        System.out.println(new PetManager().quaryPetforName(pets, "小"));
    }
}
