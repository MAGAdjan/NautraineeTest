import java.util.ArrayList;
import java.util.TreeSet;

public class ClassSearcher implements ISearcher{
    private TreeSet<NameWithDate> set;

    public void refresh(String[] classNames, long[] modificationDates){
        set = new TreeSet<NameWithDate>();
        for(int i = 0; i < classNames.length; i++){
            set.add(new NameWithDate(classNames[i], modificationDates[i]));
        }
    }

    public String[] guess(String start){
        ArrayList<String> result = new ArrayList<String>();
        final int MAXLISTSIZE = 12;
        int count = 0;

        for(NameWithDate element : set){
            if(element.name.startsWith(start)){
                result.add(element.name);
                count++;
            }
            if(count == MAXLISTSIZE){
                break;
            }
        }
        return result.toArray(new String[result.size()]);
    }

    public class NameWithDate implements Comparable{
        String name;
        Long date;

        public NameWithDate(String name, long date){
            this.name = name;
            this.date = date;
        }

        public int compareTo(Object o) {
            NameWithDate nwd = (NameWithDate) o;
            int temp = nwd.date.compareTo(this.date);
            if(temp != 0){
                return temp;
            }
            return this.name.compareTo(nwd.name);
        }
    }
}
