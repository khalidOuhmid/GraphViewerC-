#include <iostream>
#include <cmath>

 class Coord{
private :
    double *x,*y ;
public :
    Coord(double xVal, double yVal){
        x = new double(xVal);
        y = new double(yVal);
    }
    Coord(){
        x, y = new double(0.0);
    }
      Coord(const Coord &other) {
        x = new double(*other.x); 
        y = new double(*other.y); 
        std::cout << "Constructeur de copie appelé !" << std::endl;
    }

    ~Coord(){
        delete x;
        delete y;
    }

    double getX(){
        return *x;
    }
    double getY(){
        return *y;
    }
    void setX(double xVal){
        x = new double(xVal);
    }
    void setY(double yVal){
        y = new double(yVal);
    }
    double getDist(Coord c){
        double dx = (x  - c.getX());
        double dy = (y - c.getY());
        return sqrt(dx*dx +dy *dy);
    }
}