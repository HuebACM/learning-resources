import java.io.*;
import java.util.*;

public class tourist extends PrintWriter {
	tourist() { super(System.out, true); }
	Scanner sc = new Scanner(System.in);
	public static void main(String[] $) {
		tourist o = new tourist(); o.main(); o.flush();
	}

	static final int MD = 1000000007;
	int[] oo, oj; int __ = 1;
	int link(int o, int j) {
		oo[__] = o; oj[__] = j;
		return __++;
	}
	int[] ae, pp, dd;
	static class E {
		int u, v, w;
		E(int u, int v, int w) {
			this.u = u; this.v = v; this.w = w;
		}
	}
	E[] ee;
	int m, s, t;
	int[] qu;
	int[] prev, next; boolean[] used;
	int[] ff;
	void init(int n) {
		oo = new int[1 + (n - 1) * 2]; oj = new int[1 + (n - 1) * 2];
		ae = new int[n]; pp = new int[n]; dd = new int[n];
		ee = new E[n * 3];
		s = n; t = n + 1;
		qu = new int[n];
		prev = new int[n + 2]; next = new int[n + 2]; used = new boolean[n + 2];
		ff = new int[n];
		long f = 1;
		for (int i = 0; i < n; i++) {
			ff[i] = (int) f;
			f = f * (i + 1) % MD;
		}
	}
	void dfs(int p, int i, int d) {
		pp[i] = p; dd[i] = d;
		for (int o = ae[i]; o != 0; o = oo[o]) {
			int j = oj[o];
			if (j != p)
				dfs(i, j, d + 1);
		}
	}
	boolean add(int i, int j, int n) {
		int hi = 0, hj = n - 1;
		qu[hi++] = i;
		qu[hj--] = j;
		while (i != j)
			if (dd[i] >= dd[j])
				qu[hi++] = i = pp[i];
			else
				qu[hj--] = j = pp[j];
		hj += 2;
		while (hj < n)
			qu[hi++] = qu[hj++];
		if (m + hi > n * 3)
			return false;
		ee[m++] = new E(s, qu[0], qu[1]);
		for (int h = 1; h < hi - 1; h++)
			ee[m++] = new E(qu[h - 1], qu[h], qu[h + 1]);
		ee[m++] = new E(qu[hi - 2], qu[hi - 1], t);
		return true;
	}
	int count(int i, int l, int r) {
		prev[s] = next[s] = -1; used[s] = false;
		prev[t] = next[t] = -1; used[t] = false;
		for (int o = ae[i]; o != 0; o = oo[o]) {
			int j = oj[o];
			prev[j] = next[j] = -1; used[j] = false;
		}
		for (int h = l; h < r; h++) {
			E e = ee[h];
			if (next[e.u] != -1 || prev[e.w] != -1)
				return 0;
			next[e.u] = e.w; prev[e.w] = e.u;
		}
		int k = 1;
		for (int j = s; j != -1; j = next[j])
			used[j] = true;
		boolean st = used[t];
		for (int o = ae[i]; o != 0; o = oo[o]) {
			int j = oj[o];
			if (prev[j] == -1) {
				k++;
				for ( ; j != -1; j = next[j])
					used[j] = true;
			}
		}
		if (!used[t])
			k++;
		for (int o = ae[i]; o != 0; o = oo[o]) {
			int j = oj[o];
			if (!used[j])
				return 0;
		}
		if (st)
			return k == 1 ? 1 : 0;
		return ff[k - 2];
	}
	void main() {
		int n = sc.nextInt();
		init(n);
		for (int h = 0; h < n - 1; h++) {
			int i = sc.nextInt() - 1;
			int j = sc.nextInt() - 1;
			ae[i] = link(ae[i], j);
			ae[j] = link(ae[j], i);
		}
		dfs(-1, 0, 0);
		for (int i = 0; i < n; i++) {
			int j = sc.nextInt() - 1;
			if (j < 0)
				continue;
			if (j == i || !add(j, i, n)) {
				println(0);
				return;
			}
		}
		Arrays.sort(ee, 0, m, (e, f) -> e.v - f.v);
		long ans = 1;
		for (int i = 0, l = 0, r = 0; i < n; i++, l = r) {
			r = l;
			while (r < m && ee[r].v == i)
				r++;
			ans = ans * count(i, l, r) % MD;
			if (ans == 0) {
				println(0);
				return;
			}
		}
		println(ans);
	}
}
