package com.pm.ypt;

public class ToDo {
        private String todo;
        private boolean isDone = false;

        public ToDo(String todo){
                this.todo = todo;
        }

        public void setTodo(String todo) {
                this.todo = todo;
        }

        public void setState(boolean isDone) {
                this.isDone = isDone;
        }

        public String getTodo() {
                return todo;
        }

        public boolean getState() {
                return isDone;
        }

}
