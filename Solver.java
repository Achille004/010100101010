import javax.crypto.spec.SecretKeySpec;

public class Solver {

    static String title = "░█░█░░█░█░█░";

    // A-J for single title, then 0-9 for character position
    static String[] songTitles = {
        "HAREKLAVIT", // HAREKLAVIT
        "TILASKAROT", // TILASKAROT
        "GEBUERJEIT", // GEBUERJEIT
        "FARBASKEIN", // FARBASKEIN
        "ASGRETALOS", // ASGRETALOS
        "DILERKEFET", // DILERKEFET
        "BAREGERESK", // BAREGERESK
        "LADIKERFOS", // LADIKERFOS
        "SEVERKETOR", // SEVERKETOR
        "VARECKJEIT"  // VARECKJEIT
    };

    static String[] keyLetters = {
        "A8D9",
        "D1C2H9I1D9J9H3E0",
        "H0C3J4D8D5",
        "F9C1D9G5G0J2A1C1",
        "J0B1D9J4C3D9B0"
    };

    static String obfsText = """
            ░he█ ░ t░█ug█t ░ l█s░ █░ wa░
            a░d t█░ vo░█ tu░n█d ░n█░ p█i░
            T░ th░ un░██wn ░█v░ s█░ sa█░
            F█░w░rd ░█l th░ n█░ht █n░ d░█

            █ea░░hin█ ░o█ ░ c░█e ░o d░r█
            Th█░ █o░eve█ ░░f█ ░ ░░█k
            ░n m░ █o█░ a█ou░ ░░ █ra░█
            █o█ ░ █░are█ ░░ ░om█ ba░░

            ░ ░e█ s░░l█ fro█ an░ie█░ █ei░ns
            Th░█ i░ b█ea░░ng a░░ ░he █hai██
            Ne░ d░░e█░ion░ w░er█ █o ██in░
            █l░ █░e di░██s ░ c░█ p░a░

            ░oi█ ░e █░w i░t█ th█░ m█░░
            ░it░a█ m░de █░ z░█o █n░ o░e█
            Pr█░r█░░ed co█e to █a░se █ou░ ░ear░
            M█░ th░ █el░b░a░█on ░█a░█
            """;

    static String aesBlock = "TQjR54nGyFNMUC5vEXgXsOcOQ7MsO8d0NKPMHckfwdO/TLCtXoWk4rjdUIm06KrCUgUnesufnmZ26NL2dkGnXH4sUXdzw7NQgGchClGkTZO8EmGSvT2zQw/mQ7Q3a21n/UajyT693x7QAGzfOOql9EforTToSz5JoDeZMI5Zp7+TjTnPtWYFSmawzBDnTLWHPjhV4dbqx0bzJhk4D+iJkRWrVeYcLgJe/jpHj/oeqb8g9O0qRwgH4qhU6zQSsPicZnRzxyc9PfF7MwhBNKon0I+TFJrlfGXL1J7ZcYON46WQp4Uk7h9ZC+wQJMfKj5PW90x1AIVtHXWgy4XsHbVylyH1WtVEDgEgeVROZ3UEPrTh6bFgwOvmsb4GNRPuMKQbyLjajLUpa5bYYpc1Pd4k16iBzWniPjhGN3You3d/ym9R9By1hVUZj+uHWSDB023BbloaCpVHHcoDyBKM7bBkkjEveDVf9tI9HcLjofhycHkm/bYMXI61/wF8dv4wJdXVM/GiZVUWpjGeoCUnERbi7zD4ttxZ4sNGnt9aMmHtlVg6cXcRIU6mfzBTlz3dz/pxbVK/ROBca1XgynyH3uix6Zu55M7ZdAFaF0zsdnAH9w4/h3qZx0EEzORWC7o9CUjWaJrv3xoJTvaNv+QoGoFAGY2OTSLerUvAAdbHt3H6mI6HY6RoumjcDVAgS74/EFrCe2ZUCJWUBSRs7ucqVYuq8Zt3tisX8MhLak1QOpEdpMnU7/Bov2LPTmYp4j5SNaaveibE4kghIiFtzENHmT9dcqd8CPPyOcL0Yxa8aFI+uL56NgpfyIdPTLrAcNiSE+NvdC/BOSeNkSyZwFRZFKslEZ8pUuKGw2CJWgbGLdI0Azlg1fTwXMczGXPjuOmqsQ4iyU/pg21l8vxSAh64BrQrxZy1jQptzhcl1iqeXA6bDUxaq/Ik+OLcR6QOfXAyPm0y";

    static String getHint() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < keyLetters.length; i++) {
            String key = keyLetters[i];
    
            for (int j = 0; j < key.length(); j += 2) {
                int row = key.charAt(j)- 'A';
                int col = key.charAt(j + 1) - '0';
                result.append(songTitles[row].charAt(col));
            }

            result.append(" ");
        }

        return result.toString().trim();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hm, let's see what we have here...");
        System.out.println("The album title, a 10x10 grid of song titles, some coordinates, some obfuscated text and an AES block.\n");


        String hint = getHint();
        System.out.println("Well this is what we obtain by using the coordinates in the titles grid: " + hint);
        System.out.println(" -> Mh, this seems like a hint, lemme dig deeper...\n");
        
        String binTitle = title.replace("░", "0").replace("█", "1");
        System.out.println("Album title: " + title + " => In binary: " + binTitle + " (" + binTitle.length() + " chars)");
        System.out.println(" -> This is interesting\n");
        
        SecretKeySpec secretKey = AES.getKey(binTitle);
        String decrypted = AES.decrypt(aesBlock, secretKey);
        
        // Normalize line endings and get code points
        int[] obfsCodePoints = obfsText.trim().replace("\r\n", "\n").replace("\r", "\n").codePoints().toArray();
        int[] decryptedCodePoints = decrypted.replace("\r\n", "\n").replace("\r", "\n").codePoints().toArray();
        
        System.out.println("So decrypting the AES block with the derived binary title as key gives:");
        
        for (String line : decrypted.split("\n")) System.out.println("    " + line);
        System.out.println();

        System.out.println("This looks very similar to the obfuscated text:");
        System.out.println(" -> Obfuscated text length: " + obfsCodePoints.length);
        System.out.println(" -> Decrypted text length: " + decryptedCodePoints.length);

        if (obfsCodePoints.length == decryptedCodePoints.length) {
            System.out.println("  -> Lengths match, how convenient!\n");
        } else {
            System.out.println("  -> Lengths differ, something is off...");
            return;
        }

        System.out.println("Now I'll try combining them:");
        StringBuilder combined = new StringBuilder();
        for (int i = 0; i < obfsCodePoints.length; i++) {
            int obfsChar = obfsCodePoints[i];
            int decryptedChar = decryptedCodePoints[i];
            
            // If obfuscated character is a block character, use the decrypted character
            if (obfsChar == decryptedChar) {
                // Characters match, keep as is
                combined.appendCodePoint(obfsChar);
            } else {
                if (obfsChar != '░' && obfsChar != '█') {
                    combined.appendCodePoint(obfsChar);
                } else if (decryptedChar != '░' && decryptedChar != '█') {
                    combined.appendCodePoint(decryptedChar);
                } else {
                    combined.appendCodePoint('█'); // Placeholder for not found
                }
            }
        }
        
        String combinedStr = combined.toString();
        for (String line : combinedStr.split("\n")) System.out.println("    " + line);
        System.out.println();

        System.out.println("And I'd say the missing characters can be easily filled in.");
        System.out.println(" -> And that's it! May the celebration start.\n");

        System.out.println("PRAISE THE CODE");
        System.out.println("""
                 ░░░█░░█░░█░░░
                 ░░░▓░░▓░░▓░░░
                 █░░▒▒▒▓▒▒▒░░█
                 ▓░░░░░▓░░░░░▓
                 ▒▒▒▒▓▓▓▓▓▒▒▒▒
                 ░░░░░░▓░░░░░░
                 ░░█▓▒▒▓▒▒▓█░░
                 ░░░░░▓░▓░░░░░
                 ░░░░▓░░░▓░░░░
                 ░░░░░▓▓▓░░░░░
                """);
    }
}