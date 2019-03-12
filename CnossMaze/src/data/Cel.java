package data;

import java.util.List;

/**
 * Класс Cel выполняет роль конструктора
 * Принимает в себе координаты x и y, содержит информацию
 * о границах клетки и имеет лист содержащий информацию о себе
 * для создания множеств
 */
public class Cel{
    public boolean right,down;
    public List<Cel> set;
    public int x,y;

    Cel(int a,int b){
        x=a;
        y=b;
        right=false;
        down=true;
        set=null;
    }

    @Override
    public String toString()
    {
        return String.format("%d : %d", this.x, this.y);
    }
}
