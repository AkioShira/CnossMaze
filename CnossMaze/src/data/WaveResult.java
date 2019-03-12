package data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Schischko A.A.
 * @version: 1.0
 * @date: 01.11.2017
 * Класс WaveResult реализует волновой алгоритм для поиска выхода из лабиринта
 * Вызываемые методы:
 * Wave(int [][] matrix, int x1, int y1, int x2, int y2) - поиск пути, принимает
 *                          массив с лабиринтом (-1: граница, 0: пустая клетка)
 *                          и координаты начала и конца
 * getResultList() - возвращает список с координатами пути
 * getMaze() - возвращает лабиринт, где 1 - выход из него
 */
public class WaveResult {
    private int[][] wave;
    private List<Cel> waveBase = new ArrayList<>();
    private String[] waveString;

    /**
     * Функция распространения волны.
     * Из начального элемента распространяется в 4-х направлениях
     * волна.Элемент в который пришла волна образует фронт волны.
     * Каждый элемент первого фронта волны является источником
     * вторичной волны. Элементы второго фронта волны генерируют
     * волну третьего фронта и т.д. Процесс продолжается до тех пор
     * пока не будет достигнут конечный элемент.
     * @param x1,y1 - начальная точка
     * @param x2,y2 - конечная точка
     */
    private void motionWave(int x1, int y1, int x2, int y2)
    {
        waveBase.add(new Cel(x1, y1));
        wave[x1][y1]=1;
        while (wave[x2][y2]==0)
        {
            int x, y;
            /**
             * Сохраняем координаты прошлого фронта волны
             * т.к. новый распространяется в зависимости от них
             */
            List<Cel> step = new ArrayList<>();
            step.addAll(waveBase);
            waveBase.clear();
            for(Cel entry : step)
            {
                x=entry.x;
                y=entry.y;
                if(wave[x][y-1] == 0) {
                    wave[x][y-1] = wave[x][y]+1;
                    waveBase.add(new Cel(x, y-1));
                }
                if(wave[x][y+1] == 0) {
                    wave[x][y+1] = wave[x][y]+1;
                    waveBase.add(new Cel(x, y+1));
                }
                if(wave[x-1][y] == 0) {
                    wave[x-1][y] = wave[x][y]+1;
                    waveBase.add(new Cel(x-1, y));
                }
                if(wave[x+1][y] == 0) {
                    wave[x+1][y] = wave[x][y]+1;
                    waveBase.add(new Cel(x+1, y));
                }
            }
        }
        waveBase.clear();
    }

    /** Функция восстановления волны
     * Строится сама трасса. Её построение осуществляется
     * от конечного элемента к начальному.
     * @param x1,y1 - начальная точка
     * @param x2,y2 - конечная точка
     */
    private void returnWave(int x1, int y1, int x2, int y2)
    {
        int step=wave[x2][y2];
        waveBase.add(new Cel(x2, y2));
        /**
         * Каждый следующий элемент должен быть на 1 меньше
         * текущего. Таким образом алгоритм приводит
         * нас к началу.
         */
        while(wave[x1][y1]!=0)
        {
            if(wave[x2][y2-1] == step-1)
            {
                waveBase.add(new Cel(x2, y2-1));
                y2--;
                step=wave[x2][y2];
                wave[x2][y2]=0;
            }
            else if (wave[x2][y2+1] == step-1)
            {
                waveBase.add(new Cel(x2, y2+1));
                y2++;
                step=wave[x2][y2];
                wave[x2][y2]=0;
            }
            else if (wave[x2-1][y2] == step-1)
            {
                waveBase.add(new Cel(x2-1, y2));
                x2--;
                step=wave[x2][y2];
                wave[x2][y2]=0;
            }
            else if (wave[x2+1][y2] == step-1)
            {
                waveBase.add(new Cel(x2+1, y2));
                x2++;
                step=wave[x2][y2];
                wave[x2][y2]=0;
            }
        }
    }

    /**
     * Основная функция класса. Из неё вызываются все методы
     * @param matrix - входной лабиринт
     * @param x1,y1 - начальная точка
     * @param x2,y2 - конечная точка
     */
    void generateWave(int [][] matrix, int x1, int y1, int x2, int y2) {
        int mx = matrix[0].length, my = matrix.length;
        wave = new int[mx][my];
        for (int i = 0; i<matrix[0].length; i++)
        {
            for (int j = 0; j<matrix.length; j++)
            {
                wave[i][j] = matrix[i][j];
            }
        }
        motionWave(x1, y1, x2, y2);
        returnWave(x1, y1, x2, y2);
        waveString = new String[waveBase.size()];
        for(int i=0; i<waveBase.size(); i++)
        {
            waveString[i]=waveBase.get(i).toString();
        }
        wave=matrix;
        for(Cel entry : waveBase)
        {
            wave[entry.x][entry.y] = 1;
        }
    }

    /**
     * Возвращаем список координат
     * @return список
     */
    public List<Cel> getResultList()
    {
        return this.waveBase;
    }

    /**
     * Возвращаем лабиринт, в котором:
     * -1: граница
     * 0: пустая клетка
     * 1: путь выхода
     * @return лабиринт с построенной трассой
     */
    public int[][] getMaze()
    {
        return wave;
    }
}
