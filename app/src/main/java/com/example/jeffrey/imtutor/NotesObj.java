package com.example.jeffrey.imtutor;

public class NotesObj{

    private String Name;
    private String Url;
    private String Class;
    public NotesObj(){

    }
        public NotesObj(String name, String imageUrl, String classes) {
            if (name.trim().equals("")) {
                name = "No Name";
            }

            Name = name;
            Url = imageUrl;
            Class = classes;
        }

        public String getNotesName() {
            return Name;
        }

        public void setNotesName(String name) {
            Name = name;
        }

        public String getImageUrl() {
            return Url;
        }

        public void setImageUrl(String imageUrl) {
            Url = imageUrl;
        }

        public String getNotesClass() {
        return Class;
        }

        public void setNotesClass(String newwclass) {
        Class = newwclass;
        }

}
