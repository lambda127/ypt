package com.pm.ypt;

public class Subject {
        private String sName;
        private long time = 0;

        public Subject(String sName){
                this.sName = sName;
        }

        public void setsName(String sName) {
                this.sName = sName;
        }

        public void setTime(long time) {
                this.time = time;
        }

        public String getsName() {
                return sName;
        }

        public long getTime() {
                return time;
        }

}
