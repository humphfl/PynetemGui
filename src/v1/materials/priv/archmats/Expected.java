package v1.materials.priv.archmats;

import v1.materials.priv.Host;
import v1.materials.priv.Router;
import v1.materials.priv.Switch;
import v1.materials.priv.abs.Connect;
import v1.materials.priv.abs.Interface;
import v1.materials.priv.abs.Terminal;

/**
 * Enumération des types d'équipements
 */
public enum Expected {

    TERMINAL() {
        @Override
        public Terminal getCasted(Object o) {

            if (o instanceof Terminal) {
                return (Terminal) o;
            }
            return null;
        }
    },
    HOST() {
        @Override
        public Host getCasted(Object o) {

            if (o instanceof Host) {
                return (Host) o;
            }
            return null;
        }
    },
    SWITCH() {
        @Override
        public Switch getCasted(Object o) {

            if (o instanceof Switch) {
                return (Switch) o;
            }
            return null;
        }
    },
    ROUTER() {
        @Override
        public Router getCasted(Object o) {

            if (o instanceof Router) {
                return (Router) o;
            }
            return null;
        }
    },

    CONNECT() {
        @Override
        public Connect getCasted(Object o) {

            if (o instanceof Connect) {
                return (Connect) o;
            }
            return null;
        }
    },
    INTERFACE() {
        @Override
        public Interface getCasted(Object o) {

            if (o instanceof Interface) {
                return (Interface) o;
            }
            return null;
        }
    },
    NODE() {
        @Override
        public Interface getCasted(Object o) {

            if (o instanceof Interface) {
                return (Interface) o;
            }
            return null;
        }
    },

    LINK(){
        @Override
        public Connect[] getCasted(Object o) {
            if (o instanceof Connect[]) {
                return (Connect[]) o;
            }
            return null;
        }
    },

    NULL() {
        @Override
        public Object getCasted(Object o) {
            return null;
        }
    };



    Expected(){}

    public abstract <T> T getCasted(Object o);

}
