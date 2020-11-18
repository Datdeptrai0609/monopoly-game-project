package monopoly;

class Board {
	private final int N = 32; //number of blocks in a board
	private final Block[] board; //representation of board
  private int BUS_BLOCK;
  private int JAIL_BLOCK;

	//constructor for a new board of squares
	public Board() {
		board = new Block[N];
		//initialize board 
		for (int i = 0; i < N; i++)
			board[i] = makeBlock(i);

		makeTravel();
	}

	public int size() {
		return N;
	}

	public Block block(int pos) {
		return board[pos];
	}

	//return an array of the squares on the board
	public Block[] getBoard() {
		return board;
	}

	private Block makeBlock(int pos) {
		switch (pos) {
			case 0:
				return go(pos);
			case 1:
				return CaMau(pos);
			case 2:
				return chance(pos);
			case 3:
				return DaLat(pos);
			case 4:
				return VungTau(pos);
			case 5:
				return LangSon(pos);
			case 6:
				return ThaiBinh(pos);
			case 7:
				return NinhBinh(pos);
			case 8:
				return jail(pos);
			case 9:
				return DoSon(pos);
			case 10:
				return Pleiku(pos);
			case 11:
				return NinhThuan(pos);
			case 12:
        return chance(pos);
			case 13:
				return TayNinh(pos);
			case 14:
				return HaLong(pos);
			case 15:
				return HaiPhong(pos);
			case 16:
				return festival(pos);
			case 17:
				return ThanhHoa(pos);
			case 18:
				return NhaTrang(pos);
			case 19:
				return Sapa(pos);
			case 20:
				return chance(pos);
			case 21:
				return HoiAn(pos);
			case 22:
				return Hue(pos);
			case 23:
				return DaNang(pos);
			case 24:
				return bus(pos);
			case 25:
				return PhuQuoc(pos);
			case 26:
				return BinhDuong(pos);
			case 27:
				return CanTho(pos);
			case 28:
        return chance(pos);
			case 29:
				return SaiGon(pos);
			case 30:
				return tax(pos);
			case 31:
				return HaNoi(pos);
			default:
				return null;
		}
	}

	private void makeTravel() {
		TravelBlock a = (TravelBlock) block(4);
		TravelBlock b = (TravelBlock) block(9);
		TravelBlock c = (TravelBlock) block(14);
		TravelBlock d = (TravelBlock) block(18);
		TravelBlock e = (TravelBlock) block(25);

		a.createGroup(b, c, d, e);
		b.createGroup(a, c, d, e);
		c.createGroup(a, b, d, e);
		d.createGroup(a, b, c, e);
		e.createGroup(a, b, c, d);
	}

	private Block VungTau(int pos) {
		return new TravelBlock("Vung Tau", pos);
	}

	private Block PhuQuoc(int pos) {
		return new TravelBlock("Phu Quoc", pos);
	}

	private Block NhaTrang(int pos) {
		return new TravelBlock("Nha Trang", pos);
	}

	private Block HaLong(int pos) {
		return new TravelBlock("Ha Long", pos);
	}

	private Block DoSon(int pos) {
		return new TravelBlock("Do Son", pos);
	}

	private Block tax(int pos) {
		return new TaxBlock(pos);
	}

	private Block jail(int pos) {
    JAIL_BLOCK = pos;
		return new JailBlock(pos);
	}

	private Block chance(int pos) {
		return new ChanceBlock(pos);
	}

	private Block Pleiku(int pos) {
		int rent = 4;
		int oneH = 20;
		int twoH = 60;
		int threeH = 180;
		int fourH = 320;
		int hotel = 450;
		int cost = 60;
		int houses = 50;
		return new PropertyBlock("Pleiku", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block NinhBinh(int pos) {
		int rent = 4;
		int oneH = 20;
		int twoH = 60;
		int threeH = 180;
		int fourH = 320;
		int hotel = 450;
		int cost = 60;
		int houses = 50;
		return new PropertyBlock("Ninh Binh", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block LangSon(int pos) {
		int rent = 4;
		int oneH = 20;
		int twoH = 60;
		int threeH = 180;
		int fourH = 320;
		int hotel = 450;
		int cost = 60;
		int houses = 50;
		return new PropertyBlock("Lang Son", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block go(int pos) {
		return new GoBlock(pos);
	}

  private Block festival(int pos) {
    return new FestivalBlock(pos);
  }

  private Block bus(int pos) {
    BUS_BLOCK = pos;
    return new BusBlock(pos);
  }

	private Block CaMau(int pos) {
		int rent = 2;
		int oneH = 10;
		int twoH = 30;
		int threeH = 90;
		int fourH = 160;
		int hotel = 250;
		int cost = 60;
		int houses = 50;
		return new PropertyBlock("Ca Mau", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block DaLat(int pos) {
		int rent = 4;
		int oneH = 20;
		int twoH = 60;
		int threeH = 180;
		int fourH = 320;
		int hotel = 450;
		int cost = 60;
		int houses = 50;
		return new PropertyBlock("Da Lat", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block ThaiBinh(int pos) {
		int rent = 6;
		int oneH = 30;
		int twoH = 90;
		int threeH = 270;
		int fourH = 400;
		int hotel = 550;
		int cost = 100;
		int houses = 50;
		return new PropertyBlock("Thai Binh", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block ThanhHoa(int pos) {
		int rent = 6;
		int oneH = 30;
		int twoH = 90;
		int threeH = 270;
		int fourH = 400;
		int hotel = 550;
		int cost = 100;
		int houses = 50;
		return new PropertyBlock("Thanh Hoa", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block HaiPhong(int pos) {
		int rent = 8;
		int oneH = 40;
		int twoH = 100;
		int threeH = 300;
		int fourH = 450;
		int hotel = 600;
		int cost = 120;
		int houses = 50;
		return new PropertyBlock("Hai Phong", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block NinhThuan(int pos) {
		int rent = 10;
		int oneH = 50;
		int twoH = 150;
		int threeH = 450;
		int fourH = 625;
		int hotel = 750;
		int cost = 140;
		int houses = 100;
		return new PropertyBlock("Ninh Thuan", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block TayNinh(int pos) {
		int rent = 10;
		int oneH = 50;
		int twoH = 150;
		int threeH = 450;
		int fourH = 625;
		int hotel = 750;
		int cost = 140;
		int houses = 100;
		return new PropertyBlock("Tay Ninh", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block Hue(int pos) {
		int rent = 12;
		int oneH = 60;
		int twoH = 180;
		int threeH = 500;
		int fourH = 700;
		int hotel = 900;
		int cost = 160;
		int houses = 100;
		return new PropertyBlock("Hue", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block Sapa(int pos) {
		int rent = 16;
		int oneH = 80;
		int twoH = 220;
		int threeH = 600;
		int fourH = 800;
		int hotel = 1000;
		int cost = 200;
		int houses = 100;
		return new PropertyBlock("Sapa", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block HoiAn(int pos) {
		int rent = 18;
		int oneH = 90;
		int twoH = 250;
		int threeH = 700;
		int fourH = 875;
		int hotel = 1050;
		int cost = 220;
		int houses = 150;
		return new PropertyBlock("Hoi An", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block DaNang(int pos) {
		int rent = 18;
		int oneH = 90;
		int twoH = 250;
		int threeH = 700;
		int fourH = 875;
		int hotel = 1050;
		int cost = 220;
		int houses = 150;
		return new PropertyBlock("Da Nang", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block BinhDuong(int pos) {
		int rent = 22;
		int oneH = 110;
		int twoH = 330;
		int threeH = 800;
		int fourH = 975;
		int hotel = 1150;
		int cost = 260;
		int houses = 150;
		return new PropertyBlock("Binh Duong", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block CanTho(int pos) {
		int rent = 24;
		int oneH = 110;
		int twoH = 330;
		int threeH = 800;
		int fourH = 975;
		int hotel = 1150;
		int cost = 260;
		int houses = 150;
		return new PropertyBlock("Can Tho", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block SaiGon(int pos) {
		int rent = 1000;
		int oneH = 120;
		int twoH = 360;
		int threeH = 850;
		int fourH = 1025;
		int hotel = 1200;
		int cost = 280;
		int houses = 150;
		return new PropertyBlock("Sai Gon", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

	private Block HaNoi(int pos) {
		int rent = 1000;
		int oneH = 130;
		int twoH = 390;
		int threeH = 900;
		int fourH = 1100;
		int hotel = 1275;
		int cost = 300;
		int houses = 200;
		return new PropertyBlock("Ha Noi", pos, rent, oneH, twoH, threeH, fourH, hotel, cost, houses);
	}

  public int busPos() {
    return BUS_BLOCK;
  }

  public int jailPos() {
    return JAIL_BLOCK;
  }
}
