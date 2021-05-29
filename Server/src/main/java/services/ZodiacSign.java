package services;

import io.grpc.Status;
import proto.Zodiac;
import proto.Zodiac.*;
import proto.ZodiacServiceGrpc;

import java.util.ArrayList;

public class ZodiacSign extends ZodiacServiceGrpc.ZodiacServiceImplBase {

    public boolean validation(String date) {
        if(date.length() != 10)
            return false;
        if(date.charAt(2) != '/')
            return false;
        if(date.charAt(5) != '/')
            return false;
        for(int i=0;i<date.length();i++) {
            if (i != 2 && i != 5)
                if (date.charAt(i) < '0' || date.charAt(i) > '9')
                    return false;
        }

        int day = date.charAt(4)-48 + (date.charAt(3)-48)*10;
        int month = date.charAt(1)-48 + (date.charAt(0)-48)*10;
        int year = date.charAt(9)-48 + (date.charAt(8)-48)*10 + (date.charAt(7)-48)*100 + (date.charAt(6)-48)*1000;

        if(month < 1 || month > 12)
            return false;

        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if(day < 1 || day > 31)
                return false;
        }
        if(month == 4 || month == 6 || month == 9 || month == 11) {
            if(day < 1 || day > 30)
                return false;
        }
        if(month == 2) {
            if(year % 4 == 0) {
                if(day < 1 || day > 29)
                    return false;
            }
            else if(day < 1 || day > 28)
                return false;
        }

        return true;
    }

    @Override
    public void getSign(Zodiac.SignRequest request, io.grpc.stub.StreamObserver<Zodiac.SignResponse> responseObserver) {
        System.out.println("Get zodiac sign ");

        SignResponse.Builder response = SignResponse.newBuilder();
        Zodiac.SignResponse.Builder sign = null;


        String date = request.getDate();
        int day = date.charAt(4)-48 + (date.charAt(3)-48)*10;
        int month = date.charAt(1)-48 + (date.charAt(0)-48)*10;

        if(validation(date)) {
            int size = 24;
            ArrayList<String> zodiiStart = new ArrayList<String>();
            ArrayList<String> zodiiEnd = new ArrayList<String>();
            ArrayList<String> signs = new ArrayList<String>();

            zodiiStart.add("01/01");
            zodiiStart.add("01/21");
            zodiiStart.add("02/01");
            zodiiStart.add("02/19");
            zodiiStart.add("03/01");
            zodiiStart.add("03/21");
            zodiiStart.add("04/01");
            zodiiStart.add("04/21");
            zodiiStart.add("05/01");
            zodiiStart.add("05/22");
            zodiiStart.add("06/01");
            zodiiStart.add("06/22");
            zodiiStart.add("07/01");
            zodiiStart.add("07/23");
            zodiiStart.add("08/01");
            zodiiStart.add("08/23");
            zodiiStart.add("09/01");
            zodiiStart.add("09/23");
            zodiiStart.add("10/01");
            zodiiStart.add("10/23");
            zodiiStart.add("11/01");
            zodiiStart.add("11/22");
            zodiiStart.add("12/01");
            zodiiStart.add("12/22");

            zodiiEnd.add("01/20");
            zodiiEnd.add("01/31");
            zodiiEnd.add("02/18");
            zodiiEnd.add("02/29");
            zodiiEnd.add("03/20");
            zodiiEnd.add("03/31");
            zodiiEnd.add("04/20");
            zodiiEnd.add("04/30");
            zodiiEnd.add("05/21");
            zodiiEnd.add("05/31");
            zodiiEnd.add("06/21");
            zodiiEnd.add("06/30");
            zodiiEnd.add("07/22");
            zodiiEnd.add("07/31");
            zodiiEnd.add("08/22");
            zodiiEnd.add("08/31");
            zodiiEnd.add("09/22");
            zodiiEnd.add("09/30");
            zodiiEnd.add("10/22");
            zodiiEnd.add("10/31");
            zodiiEnd.add("11/21");
            zodiiEnd.add("11/30");
            zodiiEnd.add("12/21");
            zodiiEnd.add("12/31");

            signs.add("Capricorn");
            signs.add("Varsator");
            signs.add("Varsator");
            signs.add("Pesti");
            signs.add("Pesti");
            signs.add("Berbec");
            signs.add("Berbec");
            signs.add("Taur");
            signs.add("Taur");
            signs.add("Gemeni");
            signs.add("Gemeni");
            signs.add("Rac");
            signs.add("Rac");
            signs.add("Leu");
            signs.add("Leu");
            signs.add("Fecioara");
            signs.add("Fecioara");
            signs.add("Balanta");
            signs.add("Balanta");
            signs.add("Scorpion");
            signs.add("Scorpion");
            signs.add("Sagetator");
            signs.add("Sagetator");
            signs.add("Capricorn");


            for(int index = 0; index < size; index = index + 3) {
                int startDay = zodiiStart.get(index).charAt(4)-48 + (zodiiStart.get(index).charAt(3)-48)*10;
                int startMonth = zodiiStart.get(index).charAt(1)-48 + (zodiiStart.get(index).charAt(0)-48)*10;
                int endDay = zodiiEnd.get(index).charAt(4)-48 + (zodiiEnd.get(index).charAt(3)-48)*10;

                if(month == startMonth) {
                    if(day >= startDay && day <= endDay) {
                        response.setZodiacSign(signs.get(index));
                        responseObserver.onNext(response.build());
                        responseObserver.onCompleted();
                    }
                }
            }

        }
        else {
            Status status = Status.NOT_FOUND.withDescription("Invalid date.");
            responseObserver.onError(status.asRuntimeException());
        }
    }
}
