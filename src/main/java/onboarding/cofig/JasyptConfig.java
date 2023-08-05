package onboarding.cofig;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor(@Value(value = "${jasypt.secrete-key}") String secreteKey) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(secreteKey);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPoolSize(1);
        encryptor.setConfig(config);
        return encryptor;
    }

    // 암호화 값 생성 및 확인 후 제거
//    public static void main(String[] args) {
//        StringEncryptor encryptor = new JasyptConfig().stringEncryptor("HelloWorld");
//        System.out.println(encryptor.encrypt("jdbc:mysql://43.201.113.124:3306/board"));
//        System.out.println(encryptor.encrypt("colt"));
//        System.out.println(encryptor.encrypt("admin"));
//        System.out.println(encryptor.encrypt("jdbc:mysql://admin.cknhjwsq0orm.ap-northeast-2.rds.amazonaws.com:3306/board"));
//        System.out.println(encryptor.encrypt("admin"));
//        System.out.println(encryptor.encrypt("gkskatl3104"));
//    }
}
