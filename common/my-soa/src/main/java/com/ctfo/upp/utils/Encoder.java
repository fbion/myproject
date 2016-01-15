package com.ctfo.upp.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;




public class Encoder {

    public final static String getMD5String(String msg){
       
        return getPasswordEncoder().encode(msg);

    }
    
    static private DefaultPasswordEncoder pEncoder = null;
    public synchronized static DefaultPasswordEncoder  getPasswordEncoder(){
    	if( pEncoder == null	){
    		pEncoder = new DefaultPasswordEncoder("MD5");
    	}
    	return pEncoder;
    }
    //clone from cas
    static class DefaultPasswordEncoder {
    	

        private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


        private final String encodingAlgorithm;

        private String characterEncoding;

        public DefaultPasswordEncoder(final String encodingAlgorithm) {
            this.encodingAlgorithm = encodingAlgorithm;
        }

        public String encode(final String password) {
            if (password == null) {
                return null;
            }

            try {
                MessageDigest messageDigest = MessageDigest
                    .getInstance(this.encodingAlgorithm);

                if (this.characterEncoding != null && !"".equals(this.characterEncoding.trim())) {
                    messageDigest.update(password.getBytes(this.characterEncoding));
                } else {
                    messageDigest.update(password.getBytes());
                }


                final byte[] digest = messageDigest.digest();

                return getFormattedText(digest);
            } catch (final NoSuchAlgorithmException e) {
                throw new SecurityException(e);
            } catch (final UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Takes the raw bytes from the digest and formats them correct.
         * 
         * @param bytes the raw bytes from the digest.
         * @return the formatted bytes.
         */
        private String getFormattedText(byte[] bytes) {
            final StringBuilder buf = new StringBuilder(bytes.length * 2);

            for (int j = 0; j < bytes.length; j++) {
                buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
                buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
            }
            return buf.toString();
        }

        public final void setCharacterEncoding(final String characterEncoding) {
            this.characterEncoding = characterEncoding;
        }
    }

}

