import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import proto.Zodiac;
import proto.ZodiacServiceGrpc;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8999).usePlaintext().build();

        ZodiacServiceGrpc.ZodiacServiceStub zodiacStub = ZodiacServiceGrpc.newStub(channel);

        Scanner read = new Scanner(System.in);
        System.out.println("Date: ");
        String date = read.next();

        zodiacStub.getSign(Zodiac.SignRequest.newBuilder().setDate(date).build(), new StreamObserver<Zodiac.SignResponse>() {
            @Override
            public void onNext(Zodiac.SignResponse signResponse) {
                System.out.println(signResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error : " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {

            }
        });
    }
}
