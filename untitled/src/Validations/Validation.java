package Validations;

import Objects.Car;
import Objects.Repair;
import Objects.Trip;
import SQL.SQLDatabaseConnection;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Validation {

    public static boolean isFloatNum(String str)
    {
        char[] chars = str.toCharArray();
        int p = 0;
        int c=0;
        if(chars.length==0)
            return false;
        else {
            for (int i = 0; i < chars.length; i++) {
                if(c>1)
                    return false;
                if ((chars[i] == 48) || (chars[i] == 49) || (chars[i] == 50) || (chars[i] == 51) || (chars[i] == 52) || (chars[i] == 53) || (chars[i] == 54) || (chars[i] == 55) || (chars[i] == 56) || (chars[i] == 57) || (chars[i] == 46)) {
                    p = 0;

                    if(chars[i]==46)
                        c=c+1;
                } else
                    return false;
            }
            if (p == 0)
                return true;
            else
                return false;
        }
    }

    public static boolean isNum(String str)
    {
        char[] chars = str.toCharArray();
        int p = 0;
        if(chars.length==0)
            return false;
        else {
            for (int i = 0; i < chars.length; i++) {
                if ((chars[i] == 48) || (chars[i] == 49) || (chars[i] == 50) || (chars[i] == 51) || (chars[i] == 52) || (chars[i] == 53) || (chars[i] == 54) || (chars[i] == 55) || (chars[i] == 56) || (chars[i] == 57)) {
                    p = 0;
                } else
                    return false;
            }
            if (p == 0)
                return true;
            else
                return false;
        }
    }

    public static boolean isLetters(String str)
    {
        char[] chars = str.toCharArray();
        for (int i=0;i<chars.length;i++)
        {
            if((chars[i]<65)||(chars[i]>122))
            {
                return false;
            }
        }
        return true;
    }

    final static String DATE_FORMAT = "yyyy-MM-dd";

    public static boolean isDate(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isCar(String str)
    {
        int id = Integer.parseInt(str);
        for(Car car : SQLDatabaseConnection.carsList)
        {
            System.out.println(car.getId());
            if (car.getId()==id)
                return true;
        }
        return false;
    }

    public static boolean isRepair(String str)
    {
        int id = Integer.parseInt(str);
        for(Repair repair : SQLDatabaseConnection.repairsList)
        {
            System.out.println(repair.getId());
            if (repair.getId()==id)
                return true;
        }
        return false;
    }

    public static boolean isTrip(String str) {
        int id = Integer.parseInt(str);
        for (Trip trip : SQLDatabaseConnection.tripsList) {
            System.out.println(trip.getId());
            if (trip.getId() == id)
                return true;
        }
        return false;
    }

    public static boolean isGosNum(String str)
    {
        char[] chars = str.toCharArray();
        for (int i=0;i< chars.length;i++)
        {
            if(i==0)
            {
                if ((chars[i]<65)||(chars[i]>90))
                    return false;
            }
            if ((i==1))
            {
                if((chars[i]<49)||(chars[i]>57))
                    return false;
            }
            if ((i==2)||(i==3))
            {
                if((chars[i]<48)||(chars[i]>57))
                    return false;
            }
            if((i==4)||(i==5))
            {
                if ((chars[i]<65)||(chars[i]>90))
                    return false;
            }
            if((i==6))
            {
                if((chars[i]<49)||(chars[i]>57))
                    return false;
            }
            if((i==7))
            {
                if((chars[i]<48)||(chars[i]>57))
                    return false;
            }
        }
        return true;
    }

    public static String editColor(String str)
    {
        char[] chars = str.toCharArray();
        int i = chars.length-1;
        char[] chars1 = new char[i];
        int counter = 0;

        while(counter<chars.length-1)
        {
            chars1[counter]=chars[counter];
            counter=counter+1;
        }
        String result = String.valueOf(chars1);
        return result;

    }

    public static String editPoint(String str)
    {
        char[] chars = str.toCharArray();
        int i = chars.length-1;
        char[] chars1 = new char[i];
        int counter = 0;

        while(counter<chars.length-1)
        {
            chars1[counter]=chars[counter];
            counter=counter+1;
        }
        String result = String.valueOf(chars1);
        return result;

    }


}
