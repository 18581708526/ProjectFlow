package com.lzj.练习;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kids extends ManKind{
   private int yearsOld;
 class xx{

}
   public void printAge(int yearsOld){
       System.out.println("yearsOld = " + yearsOld);
   }

    public static void main(String[] args) {

    }
}
