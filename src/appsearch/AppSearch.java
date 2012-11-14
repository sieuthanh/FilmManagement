package appsearch;

public class AppSearch {
    public static int commonChar(String x, String y) {
        int[][] f = new int[200][200];
        for (int i = 0; i < x.length(); i ++)
            for (int j = 0; j < y.length(); j ++) f[i][j] = 0;
        for (int i = 0; i < x.length(); i ++)
            for (int j = 0; j < y.length(); j ++) {
                if (x.charAt(i) == y.charAt(j)) {
                    if (i > 0 && j > 0) f[i][j] = f[i - 1][j - 1] + 1;
                    else f[i][j] = 1;
                }
                if (i > 0)
                    f[i][j] = Math.max(f[i][j], f[i - 1][j]);
                if (j > 0)
                    f[i][j] = Math.max(f[i][j], f[i][j - 1]);
            }
        return f[x.length() - 1][y.length() - 1];
    }
}
