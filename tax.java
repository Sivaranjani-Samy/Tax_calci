import java.util.*;
interface taxcalculator{
    double calculatetax(double amt);
}
class invalidamount extends Exception{
    invalidamount(String s){
        super(s);
    }
}
class notaxcalculator implements taxcalculator{
    public double calculatetax(double amt){
        return 0;
    }
    
}
class AbstractTaxCalculator implements taxcalculator{
    public double tax;
    public double calculatetax(double amt){
        if(amt<=50000){
            tax=0.2*amt;
        }
        else if(amt<=100000){
            tax=0.3*amt;
        }
        else if(amt<=200000){
            tax=0.4*amt;
        }
        else{
            tax=0.5*amt;
        }
        return tax;
    }
}
class chainedtaxcalculator implements taxcalculator{
    public ArrayList<taxcalculator>totaltax=new ArrayList<taxcalculator>();
    chainedtaxcalculator(ArrayList<taxcalculator>cal){
        this.totaltax=cal;
    }
   public double calculatetax(double amt){
       double tot=0;
       double res;
       for(int i=0;i<totaltax.size();i++){
          res=totaltax.get(i).calculatetax(amt);
          tot+=res;
          //System.out.println(totaltax.size());
       }
       
      return tot; 
   }

}
class basictaxcalculator extends AbstractTaxCalculator{
   public String name;
   public double amt;
   public double tax=0;
   basictaxcalculator(String name,double amt){
       this.name=name;
       this.amt=amt;
   }
    public double calculate(double amt){
        tax=0.3*amt;
        return tax;
    }
}
class incometax extends basictaxcalculator{
  
   incometax(String name,double amt){
      super(name,amt); 
   }
    @Override
    public double calculate(double amt){
        if(amt<100000){
           tax=0;
        }
        else if(amt<200000){
            tax=0.05*(amt-200000);
        }
        else if(amt<300000){
            tax=0.06*(amt-300000);
        }
        else{
           tax=0.08*(amt-1000000)+0.05*(amt-200000)+0.03*(amt-300000);
        }
       return tax;
    }
}
class salestax extends basictaxcalculator{
     salestax(String name,double amt){
      super(name,amt); 
   }
    @Override
    public double  calculate(double amt){
        if(amt<=100000){
           tax=0;
        }
        else if(amt<=200000){
            tax=0.2*(amt-200000);
        }
        else if(amt<=300000){
            tax=0.3*(amt-300000);
        }
        else{
            tax=0.5*(amt-100000)+0.4*(amt-200000)+0.3*(amt-300000);
        }
      return tax;
    }
}
class ecotax extends basictaxcalculator{
     ecotax(String name,double amt){
      super(name,amt); 
   }
    
    @Override
    public double calculate(double amt){
         if(amt<=100000){
            tax=0;
        }
        else if(amt<=200000){
            tax=0.25*(amt-200000);
        }
        else if(amt<=300000){
            tax=0.35*(amt-300000);
        }
        else{
            tax=0.55*(amt-1000000)+0.02*(amt-200000)+0.03*(amt-300000);
        }
        return tax;
    }
    
}
public class tax{
  public static void main(String args[])throws invalidamount{
  ArrayList<taxcalculator> tax=new ArrayList<taxcalculator>();
  System.out.println("-----TAX CALCULATOR----------");
  Scanner in=new Scanner(System.in);
  double restax;
   System.out.println("Enter Your Name:");
   String name=in.next();
   System.out.println("Enter Your Salary:");
   double sal=in.nextDouble();
   try{
   if(sal<0){
       throw new invalidamount("Amount is not valid");
   }
   basictaxcalculator bs=new basictaxcalculator(name,sal);
   System.out.println("your basic tax is:"+ bs.calculate(sal));
   System.out.println("Want to add any other tax");
   System.out.println(" 1.Abstract tax \n 2.NO Tax \n 3.Income Tax \n 4.SalesTax \n 5.Econaomical Tax \n 6.EXIT");
   int ch;
   do{
   System.out.println("Enter your choice:");
   ch=in.nextInt();
   if(ch==1){
       AbstractTaxCalculator as=new AbstractTaxCalculator();
       tax.add(as);
       restax=as.calculatetax(sal);
       System.out.println("Abstract tax:"+restax);
   }
   if(ch==2){
       notaxcalculator n=new notaxcalculator();
       tax.add(n);
       restax=n.calculatetax(sal);
       System.out.println("Abstract tax:"+restax);
   }
    if(ch==3){
       incometax n=new incometax(name,sal);
       tax.add(n);
       restax=n.calculatetax(sal);
       System.out.println("Income tax:"+restax);
   }
    if(ch==4){
        salestax s=new salestax(name,sal);
        tax.add(s);
        restax=s.calculate(sal);
          System.out.println("Sales tax:"+restax);
    }
    if(ch==5){
        ecotax e=new ecotax(name,sal);
        tax.add(e);
        restax=e.calculate(sal);
         System.out.println("Economic tax:"+restax);
    }
   }while(ch!=6);
   //system.out.println(tax.size());
   chainedtaxcalculator ct=new chainedtaxcalculator(tax);
   restax=ct.calculatetax(sal);
   System.out.println("Total tax:"+restax);
    
    
 }catch(invalidamount e){
    System.out.println(e);
}

}
}
