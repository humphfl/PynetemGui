package v2.vue.items.abstracts;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum Pics {


    HOST("/v2/vue/res/pc.png") {
        @Override
        public void make() {
            Image image = new Image(this.imgPath, true);
            pic = new ImageView(image);
            // Préserve le ration de l'image
            pic.setPreserveRatio(true);

            // prend seulement une partie de l'image
            Rectangle2D viewportRect = new Rectangle2D(0, 0, 59, 48);//Host ViewPort
            pic.setViewport(viewportRect);

        }
    },
    ROUTER("/v2/vue/res/router.png") {
        @Override
        public void make() {
            Image image = new Image(this.imgPath, true);
            pic = new ImageView(image);
            // Préserve le ration de l'image
            pic.setPreserveRatio(true);

            // prend seulement une partie de l'image
            Rectangle2D viewportRect = new Rectangle2D(0, 0, 59, 40);//Host ViewPort
            pic.setViewport(viewportRect);

        }
    },
    BRIDGE("/v2/vue/res/bridge.png") {
        @Override
        public void make() {
            Image image = new Image(this.imgPath, true);
            pic = new ImageView(image);
            // Préserve le ration de l'image
            pic.setPreserveRatio(true);

            // prend seulement une partie de l'image
            Rectangle2D viewportRect = new Rectangle2D(0, 0, 59, 48);//Host ViewPort
            pic.setViewport(viewportRect);

        }
    },
    SWITCH("/v2/vue/res/workgroup-switch.png") {
        @Override
        public void make() {
            Image image = new Image(this.imgPath, true);
            pic = new ImageView(image);
            // Préserve le ration de l'image
            pic.setPreserveRatio(true);

            // prend seulement une partie de l'image
            Rectangle2D viewportRect = new Rectangle2D(0, 0, 76, 37);//Host ViewPort
            pic.setViewport(viewportRect);

        }
    };

    protected final String imgPath;
    protected ImageView pic;

    Pics(String imgPath) {
        this.imgPath = imgPath;
    }

    public ImageView getImg() {
        make();
        return this.pic;
    }

    public abstract void make();
}
