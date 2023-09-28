import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

 class Composition {
    private String name;
    private String genre;
    private String artist;
    private String lyrics;
    private String creationDate;
    private String duration;
    private String format;
    private double rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("Composition(Ім'я='%s', Жанр='%s', Артист='%s', Текст='%s', Дата створення='%s', " +
                        "Тривалість='%s', Формат='%s', Рейтинг='%s')", name, genre, artist, lyrics, creationDate, duration,
                format, rating);
    }
}

 class Program {
    private static List<Composition> compositions = new ArrayList<>();
    private static final String FileName = "compositions.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        loadCompositions();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Меню:");
            System.out.println("1. Добавити нову композицію");
            System.out.println("2. Переглянути список композиций");
            System.out.println("3. Сортування композиції по імені");
            System.out.println("4. Сортування композиції по виконавцю");
            System.out.println("5. Сортування композиції по рейтингу");
            System.out.println("6. Вихід");
            System.out.print("Виберіть функцію: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addComposition();
                    break;

                case 2:
                    displayCompositions();
                    break;

                case 3:
                    Collections.sort(compositions, Comparator.comparing(Composition::getName));
                    System.out.println("Композиції відсортовані по імені.");
                    break;

                case 4:
                    Collections.sort(compositions, Comparator.comparing(Composition::getArtist));
                    System.out.println("Композиції відсортовані по виконавцю.");
                    break;

                case 5:
                    Collections.sort(compositions, Comparator.comparingDouble(Composition::getRating).reversed());
                    System.out.println("Композиції відсортовані по рейтингу.");
                    break;

                case 6:
                    saveCompositions();
                    System.out.println("Програма завершена.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Неправильний вибір.");
                    break;
            }
        }
    }

    private static void loadCompositions() {
        File file = new File(FileName);

        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder jsonData = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    jsonData.append(line);
                }

                Composition[] compositionArray = gson.fromJson(jsonData.toString(), Composition[].class);
                Collections.addAll(compositions, compositionArray);
                System.out.println("Дані успішно завантажені із compositions.json");
            } catch (IOException e) {
                System.out.println("Помилка завантаження даних із compositions.json: " + e.getMessage());
            }
        } else {
            System.out.println("compositions.json не знайдено.");
        }
    }

    private static void addComposition() {
        System.out.println("Введіть дані композиції");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ім'я: ");
        String name = scanner.nextLine();

        System.out.print("Жанр: ");
        String genre = scanner.nextLine();

        System.out.print("Артист: ");
        String artist = scanner.nextLine();

        System.out.print("Текст: ");
        String lyrics = scanner.nextLine();

        System.out.print("Дата створення (yyyy-MM-dd): ");
        String creationDate = scanner.nextLine();

        System.out.print("Тривалість (hh:mm:ss): ");
        String duration = scanner.nextLine();

        System.out.print("Формат: ");
        String format = scanner.nextLine();

        System.out.print("Рейтинг: ");
        double rating = scanner.nextDouble();

        Composition composition = new Composition();
        composition.setName(name);
        composition.setGenre(genre);
        composition.setArtist(artist);
        composition.setLyrics(lyrics);
        composition.setCreationDate(creationDate);
        composition.setDuration(duration);
        composition.setFormat(format);
        composition.setRating(rating);

        compositions.add(composition);
        System.out.println("Композиція добавлена");
    }

    private static void displayCompositions() {
        for (Composition composition : compositions) {
            System.out.println(composition);
        }
    }

    private static void saveCompositions() {
        try (FileWriter writer = new FileWriter(FileName)) {
            gson.toJson(compositions, writer);
        } catch (IOException e) {
            System.out.println("Помилка при збереженні данних в compositions.json: " + e.getMessage());
        }
    }
}
