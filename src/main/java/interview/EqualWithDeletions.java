package interview;

public class EqualWithDeletions {
    public static final char BACKSPACE = '#';
    public static final char DELETE = '%';
    public static String filter(String val){
        final int size = val.length();
        StringBuilder sb = new StringBuilder();
        int toSkip = 0;
        for(int i = 0; i < size; i++){
            char c = val.charAt(i);
            if(toSkip > 0)
                toSkip += DELETE == c ? 1 : -1;
            else{
                switch (c){
                    case BACKSPACE -> {
                        if(!sb.isEmpty())
                            sb.deleteCharAt(sb.length() -1);
                    }
                    case DELETE -> toSkip++;
                    default -> sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    public static boolean equalWithDeletions(String val1, String val2){
        return filter(val1).equals(filter(val2));
    }

    public static void main(String[] args) {
        System.out.println(equalWithDeletions("a##x", "#a#x"));
        System.out.println(equalWithDeletions("fi##f%%%th %%year #time###", "fifth year time"));
        System.out.println(equalWithDeletions("fi##f%%%th %%year #time###", "fart"));
    }
}
