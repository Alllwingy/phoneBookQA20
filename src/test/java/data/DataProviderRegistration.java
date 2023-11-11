package data;

import com.github.javafaker.Faker;
import dto.UserDTOLombok;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProviderRegistration {

    Faker faker = new Faker();

    @DataProvider
    public Iterator<Object[]> data(Method method) {

        List<Object[]> list = new ArrayList<>();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password() + "A$";

        switch (method.getName()) {

            case "positiveRegistration":

                list.add(new Object[]{

                        UserDTOLombok.builder()
                                .email(email)
                                .password(password)
                                .build()
                });

                write(email, password);

                return list.iterator();

            case "negativeRegNoSymbol":

                String path = "src/test/resources/negativedata.csv";

                try (BufferedReader in = new BufferedReader(new FileReader(new File(path)))) {

                    String line;

                    while ((line = in.readLine()) != null) {

                        String[] split = line.split(",");

                        list.add(new Object[]{

                                UserDTOLombok.builder()
                                        .email(split[0])
                                        .password(split[1])
                                        .build()
                        });
                    }
                } catch (IOException e) {

                    throw new RuntimeException(e);
                }

                return list.iterator();
        }

        return null;
    }

    public void write(String email, String password) {

        String path = "src/test/resources/positivedata.csv";

        try (BufferedWriter out = new BufferedWriter(new FileWriter(path, true))) {

            out.newLine();
            out.write(email);
            out.write(",");
            out.write(password);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}
