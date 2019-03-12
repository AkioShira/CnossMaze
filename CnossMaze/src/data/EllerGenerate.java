package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: Schischko A.A.
 * @version: 1.0
 * @date: 01.11.2017
 * Класс EllerGenerate предназначен для генерации лабиринта
 * он принимает в себе значение size и возращает готовый массив.
 * Вызываемые методы:
 * EllerGenerate(int s) - конструктор, принимает размер
 * drive() - генерация лабиринта
 * getMaze() - возвращает лабиринт, где:
 *                                  -1: граница
 *                                   0: пустая клетка
 */

public class EllerGenerate {
    private static Random randomizer = new Random();
    private static int size;
    private static int[][] maze;

    EllerGenerate(int s)
    {
        size = s; size--;
    }

    /**
     * Группируем негруппированные ячейки для создания новых наборов
     * @param row - ряд
     * @return ряд с набором
     */
    private Cel[] makeSet(Cel[] row) {
        for(int index = 0; index < row.length; ) {
            Cel cell = row[index++];
            if(cell.set == null) {
                List<Cel> list = new ArrayList<Cel>();
                list.add(cell);
                cell.set=list;
            }
        }
        return row;
    }

    /**
     * Функция создания границ справо
     * Случайно решаем добавлять границу или нет. Если текущая ячейка и ячейка справа
     * принадлежат одному множеству, то создаём границу между ними.
     * Если мы решили не добавлять границу, то объединяем два множества (merge), в которых
     * находится текущая ячейка и ячейка справа
     * @param row - ряд
     * @return ряд с границами справо
     */
    private Cel[] makeRightWalls(Cel[] row) {
        for(int i = 1; i < row.length; i++) {
            if(isContainsInList(row[i-1].set,row[i])) {
                row[i-1].right=true;
                continue;
            }
            if(randomizer.nextBoolean())
                row[i-1].right=true;
            else
                row=merge(row,i);
        }
        return row;
    }

    /**
     * Функция поглощения
     * Объединяем два можества
     * @param row - множество
     * @param i - индекс элемента
     * @return объединённое множество
     */
    private Cel[] merge(Cel[] row,int i) {
        List<Cel> currentList = row[i-1].set;
        List<Cel> nextList = row[i].set;
        for(Cel j : nextList)
        {
            currentList.add(j);
            j.set=currentList;
        }
        return row;
    }

    /**
     * Функция проверки принадлежности ячейки множеству
     * @param set - проверяемое множество
     * @param cell - проверяемая ячейка
     * @return логический ответ
     */
    private boolean isContainsInList(List<Cel> set,Cel cell) {
        for(Cel i : set) {
            if(i==cell)
                return true;
        }
        return false;
    }

    /**
     * Вспомогательная функция
     * Проверяем первый элемент множества на наличие границы снизу
     * @param set - проверяемое множество
     * @return логический ответ
     */
    private boolean isNotDone(List<Cel> set){
        boolean rslt=true;
        rslt=rslt&&set.get(0).down;
        return rslt;
    }

    /**
     * Функция создания границ снизу
     * Создаём нижнюю границу у всех ячеек. После убираем их
     * случайным образом до тех пор, пока первый элемент
     * множества не останется без границы. Таким образом,
     * если ячейка в своём множестве одна, то она не будет иметь
     * границу снизу.
     * @param row - ряд
     * @return ряд с нижними границами
     */
    private Cel[] makeDown(Cel[] row){
        for(int i=0;i<row.length;i++){
            for(Cel x:row[i].set) x.down = true;
            while(isNotDone(row[i].set)){
                do{
                    row[i].set.get(randomizer.nextInt(row[i].set.size())).down=false;
                }while(randomizer.nextBoolean());
            }
        }
        return row;
    }

    /**
     * Основная функция для генерации лабиринта.
     * Из неё вызываются все методы и строится
     * лабиринт
     */
    public void driver(){
        maze=new int[size+1][size+1];
        Cel[] cur=new Cel[size/2];
        for(int i=0;i<size/2;i++)
            cur[i]=new Cel(0,i);
        for(int i=0;i<size/2;i++){
            cur=makeSet(cur);
            cur=makeRightWalls(cur);
            cur=makeDown(cur);
            if(i==size/2-1)
                cur=end(cur);
            printMaze(cur,i);
            if(i!=size/2-1)
                cur=genNextRow(cur);
        }
        for(int i=0;i<=size;i++)
            maze[i][0]=maze[0][i]=maze[i][size]=maze[size][i]=-1;
        for(int i=2;i<=size;i+=2)
            for(int j=2;j<=size;j+=2)
                maze[i][j]=-1;
    }

    /**
     * Функция завершения алгоритма
     * Удаляем границы справо и объединяем множества
     * @param row - ряд
     * @return - завершающий ряд лабиринта
     */
    private Cel[] end(Cel[] row)
    {
        for(int i = 1; i < row.length; i++)
        {
            if(findPos(row[i-1].set,row[i]) == -1)
            {
                row[i-1].right=false;
                row=merge(row,i);
            }
        }
        return row;
    }

    /**
     * Вспомогательная функция
     * Ищем элемент в множестве
     * @param set - множество
     * @param x - искомая ячейка
     * @return позиция
     */
    private int findPos(List<Cel> set, Cel x){
        Cel[] tmpArray = new Cel[set.size()];
        tmpArray = set.toArray(tmpArray);
        for(int i=0;i<tmpArray.length;i++)
            if(tmpArray[i]==x)
                return i;
        return -1;
    }

    /**
     * Функция продолжения алгоритма
     * Убираем границы у ячеек и их принадлежность
     * к множествам
     * @param pre - ряд
     * @return ряд без множеств и границ
     */
    private Cel[] genNextRow(Cel[] pre){
        for(int i = 0; i < pre.length;i++ ) {
            pre[i].right=false;
            pre[i].x++;
            if(pre[i].down) {
                pre[i].set.remove(findPos(pre[i].set, pre[i]));
                pre[i].set=null;
                pre[i].down=false;
            }
        }
        return pre;
    }

    /**
     * Функция отображения границ
     * Отображаем все сгенерированные границы
     * @param row - ряд
     * @param rowPos - позиция
     */
    private void printMaze(Cel[] row,int rowPos){
        rowPos=2*rowPos+1;
        for(int i=0;i<row.length;i++){
            if(row[i].right)
                maze[rowPos][2*i+2]=-1;
            if(row[i].down)
                maze[rowPos+1][2*i+1]=-1;
        }
    }

    /**
     * Возвращаем сгенерированный лабиринт, в котором
     * -1: граница
     * 0: пустая клетка
     * @return лабиринт
     */
    public int[][] getMaze()
    {
        return maze;
    }
}
