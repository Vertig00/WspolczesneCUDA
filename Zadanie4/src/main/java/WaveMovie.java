/**
 * Created by Nex0Zero on 2017-01-16.
 */

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.*;

/**
 * y(t,z) = A * sin(wt - kz + fi)
 * y - miara odchylenia od stanu równowagi
 * A - amplituda fali
 * w - częstość fali
 * t - czas
 * k - wektor fali
 * z - współrzędna położenia
 * fi - faza początkowa t=0 z=0
 * <p>
 * w = 2*pi/T
 * T - okres drgań
 * <p>
 * k = 2*pi/lambda
 * lambda - długość fali
 * <p>
 * y(t,z) = A * sin(2pi/T * t - 2pi/lambda * z + fi)
 * <p>
 * T = lambda/v
 * v - prędkość rozchodzenia się fali
 */
public class WaveMovie {

    public static String ORIGINAL = "C:\\WORKSPACE\\IdeaProjects\\#DATA\\CUDA\\original.bmp";
    public static String WATER = "C:\\WORKSPACE\\IdeaProjects\\#DATA\\CUDA\\water.jpg";
    public static String WAVE = "C:\\WORKSPACE\\IdeaProjects\\#DATA\\CUDA\\wave.bmp";

    public static double VAR_A = 1.0;           // amplituda fali
    public static double VAR_lambda = 50;       // długość fali
    public static double VAR_start_t = 0;       // czas początkowy
    public static double VAR_fi = -1;            // faza początkowa t=0 z=0

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\WORKSPACE\\IdeaProjects\\WspolczesneCUDA\\Zadanie4\\src\\main\\java\\Wave.ptx");
        file.delete();

        BufferedImage image = ImageIO.read(new File(ORIGINAL));

        getDefaultVideo("wid", 100, image);
    }

    private static BufferedImage generateWaveCUDA(BufferedImage image, double t) throws IOException {
        int[] tab = WaveCUDA.obliczenieFali(image, t);

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, image.getType());

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                int val = tab[w * width + h];
                Color color = new Color(val, val, val);
                result.setRGB(w, h, color.getRGB());
            }
        }

//        File file = new File("C:\\WORKSPACE\\IdeaProjects\\#DATA\\CUDA\\img"+t+".bmp");
//        ImageIO.write(result,"bmp",file);
        return result;
    }

    private static BufferedImage generateWave(BufferedImage image, double t) {
        int centerX = image.getWidth() / 2;
        int centerY = image.getHeight() / 2;

        BufferedImage wave = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                image.getType());

        for (int w = 0; w < image.getWidth(); w++) {
            for (int h = 0; h < image.getHeight(); h++) {
                double distance = calcDistance(centerX, centerY, w, h);
                double f = fazaDlaPkt(VAR_A, VAR_lambda, t, VAR_fi, distance);
                double res = f * 127 + 127;
                int ress = (int) round(res);
                Color color = new Color(ress, ress, ress);
                wave.setRGB(w, h, color.getRGB());
            }
        }

        return wave;
    }

    private static double calcDistance(int cX, int cY, int x, int y) {
        double dx = (double) cX - (double) x;
        double dy = (double) cY - (double) y;
        double result = pow(dx, 2) + pow(dy, 2);
        result = sqrt(result);
        return result;
    }

    private static double fazaDlaPkt(double A, double lambda, double t, double fi, double distance) {
        double pi = Math.PI;
        double v = 1;
        double T = lambda / v;

        double w = 2 * pi / T;
        double k = 2 * pi / lambda;
        double f = A * sin(w * t - k * distance + fi);

        return f;
    }

    public static void getDefaultVideo(String name, int frames, BufferedImage original) throws IOException {
        int width = original.getWidth();
        int height = original.getHeight();

        File file = new File(name + ".mp4");
        IMediaWriter writer = ToolFactory.makeWriter(file.getName());


        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4, width, height);

        long start = System.currentTimeMillis();

        for (int i = 0; i < frames; i++) {

            if (i % (frames / 5) == (frames / 5) - 1)
                System.out.println("Capture frame " + (i + 1));

            BufferedImage image = generateWaveCUDA(original, i);
            image = ConverterFactory.convertToType(image, BufferedImage.TYPE_3BYTE_BGR);
            IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);

            IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
            frame.setKeyFrame(i == 0);
            frame.setQuality(0);

            writer.encodeVideo(0, frame);

            // 10 FPS
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        writer.close();

        System.out.println("Video recorded in file: " + file.getAbsolutePath());
    }


//    private static BufferedImage waveEffect(BufferedImage image, BufferedImage mask, double ratio) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//
//        BufferedImage result = new BufferedImage(width, height, image.getType());
//
//        for(int w = 0; w < width; w++) {
//            for(int h = 0; h < height; h++) {
//                Color colorI = new Color( image.getRGB(w,h) );
//                Color colorM = new Color(  mask.getRGB(w,h) );
//                double res = colorM.getRed();
//                double f = (res - 127) / 127;
//            }
//        }
//    }

}




















