package Lec8;

public class Test
{
    public static void main(String[] args)
    {
        try
        {
            double num = -25;
            double result = MyMath.mysqrt(num);
            System.out.println(result);
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.exit(0);
        }
    }
}


