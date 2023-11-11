package data;

import dto.UserDTOLombok;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProviderLogin {
    @DataProvider (name = "logintests")
    public Iterator<Object[]> dataLogin(Method method) {

        List<Object[]> list = new ArrayList<>();

        switch (method.getName()) {

            case "positiveLoginUserDtoLombok":

                list.add(new Object[]{

                        UserDTOLombok.builder()
                                .email("document@gmail.com")
                                .password("Task$12345")
                                .build()
                });

                list.add(new Object[]{

                        UserDTOLombok.builder()
                                .email("g@d.ua")
                                .password("Task$12345")
                                .build()
                });

                return list.iterator();

            case "negativeWrongPasswordNoDigits":

                list.add(new Object[]{

                        UserDTOLombok.builder()
                                .email("document@gmail.com")
                                .password("Task$")
                                .build()
                });

                list.add(new Object[]{

                        UserDTOLombok.builder()
                                .email("g@d.ua")
                                .password("Task12345")
                                .build()
                });

                list.add(new Object[]{

                        UserDTOLombok.builder()
                                .email("g@d.ua")
                                .password("$12345")
                                .build()
                });

                return list.iterator();
        }

        return null;
    }

    public String switchPath(String methodName) {

        String path;

        switch (methodName) {

            case "positiveLoginUserDtoLombok" : path = "src/test/resources/positivedata.csv"; return path;

            case "negativeWrongPasswordNoDigits" : path = "src/test/resources/negativedata.csv"; return path;
        }

        return null;
    }

    @DataProvider
    public Iterator<Object[]> loginCSV(Method method) {

        List<Object[]> list = new ArrayList<>();
        String path = switchPath(method.getName());

        try (BufferedReader in = new BufferedReader(new FileReader(new File(path)))) {

            String line = in.readLine();

            while (line != null) {

                String[] split = line.split(",");

                list.add(new Object[]{
                        UserDTOLombok.builder()
                                .email(split[0])
                                .password(split[1])
                                .build()
                });

                line = in.readLine();
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        return list.iterator();
    }
}
