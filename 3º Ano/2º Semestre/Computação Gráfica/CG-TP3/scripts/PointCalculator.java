import java.io.*;

class PointCalculator {
    public static void main(String[] args)
    {
        double x,y=-0.65,z, r=2.8;

        double trinta = 30;
        double sessenta = 60;

        double rTrinta = Math.toRadians(trinta);
        double rSessenta = Math.toRadians(sessenta);

        System.out.println(rTrinta);
        System.out.println(rSessenta);

        //Ponto 1
        x = 0;z=r;
        System.out.println("<point X=\"" + x +"\""+ " Y=\""+ y +"\"" + " Z=\"" + z + "\"/>");

        //Ponto 2
        x = r*Math.cos(rSessenta);z=r*Math.sin(rSessenta);
        System.out.println("<point X=\"" + x +"\""+ " Y=\""+ y +"\"" + " Z=\"" + z + "\"/>");

        //Ponto 3
        x = r*Math.cos(rTrinta);z=r*Math.sin(rTrinta);
        System.out.println("<point X=\"" + x +"\""+ " Y=\""+ y +"\"" + " Z=\"" + z + "\"/>");

        //Ponto 4
        x = r;z=0;
        System.out.println("<point X=\"" + x +"\""+ " Y=\""+ y +"\"" + " Z=\"" + z + "\"/>");

        //Ponto 5
        x = r*Math.cos(rTrinta);z=-r*Math.sin(rTrinta);
        System.out.println("<point X=\"" + x +"\""+ " Y=\""+ y +"\"" + " Z=\"" + z + "\"/>");

        //Ponto 6
        x = r*Math.cos(rSessenta);z=-r*Math.sin(rSessenta);
        System.out.println("<point X=\"" + x +"\""+ " Y=\""+ y +"\"" + " Z=\"" + z + "\"/>");

        //Ponto 7
        x = 0;z=-r;
        System.out.println("<point X=\"" + x +"\""+ " Y=\""+ y +"\"" + " Z=\"" + z + "\"/>");

        //Ponto 8
        x = -r*Math.cos(rSessenta);z=-r*Math.sin(rSessenta);
        System.out.println("<point X=\"" + x +"\""+ " Y=\""+ y +"\"" + " Z=\"" + z + "\"/>");
        
        //Ponto 9
        x = -r*Math.cos(rTrinta);z=-r*Math.sin(rTrinta);
        System.out.println("<point X=\"" + x +"\""+ " Y=\""+ y +"\"" + " Z=\"" + z + "\"/>");

        //Ponto 10
        x = -r;z=0;
        System.out.println("<point X=\"" + x +"\""+ " Y=\""+ y +"\"" + " Z=\"" + z + "\"/>");

        //Ponto 11
        x = -r*Math.cos(rTrinta);z=r*Math.sin(rTrinta);
        System.out.println("<point X=\"" + x +"\""+ " Y=\""+ y +"\"" + " Z=\"" + z + "\"/>");

        //Ponto 12
        x = -r*Math.cos(rSessenta);z=r*Math.sin(rSessenta);
        System.out.println("<point X=\"" + x +"\""+ " Y=\""+ y +"\"" + " Z=\"" + z + "\"/>");

    }
}