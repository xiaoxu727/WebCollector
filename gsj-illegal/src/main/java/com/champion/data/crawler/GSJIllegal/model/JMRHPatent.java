package com.champion.data.crawler.GSJIllegal.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by champion_xu on 2018/1/19.
 */
@Document(collection ="patent_list_info" )
public class JMRHPatent {

    @Field("keyWord")
    String keyWord;
    @Field("linkPath")
    String linkPath;
    @Field("pid")
    String PID;
    String TIC ;
    String TIE ;
//    标题
    String TIO;
    String INO;
    String INC;
    String INE;
    String ANO;
    String AD;
    String PD;
    String PNO;
    String APO;
    String APE;
    String APC;
    String IPCQ;
    String IPC;
    String PRNO ;
    String PRN ;
    String LC ;
    String VU ;
    String ABSO;
    String ABSE ;
    String ABSC ;
    String EFC ;
    String SFC;
    String IMGTITLE ;
    String IMGNAME ;
    String LSSC;
    String PDT;
    String PDTC;
    String MCLE;
    String CIGC ;
    String TFO ;
    String TBO ;
    String ISO ;
    String SEO ;
    String DDO ;
    String CLO ;
    String CLN;
    String DEBEC ;
    String DEBEO ;
    String DEBEE ;
    String IMGO ;
    String PDFEXIST;
    String PNS;
    String SFPNS ;
    String ASO;
    String ASC;
    String ASE;
    String DRAP;
    String eYears;
    String ABS_IMG;
    String PT ;
    String PERT ;
    String CRN ;
    String BAJDO ;
    String PCYMD ;
    String RLYMD ;
    String TRO ;
    String TRPO ;
    String TT ;
    String EYMD ;
    String PASO ;
    String AASO ;
    String ASPO ;
    String ILSSC ;
    String ILSAYMD ;
    String LSBCN ;
    String PPT ;
    String PPRS ;
    String PPEYMD ;
    String PPYMD ;
    String PPRYMD ;
    String PPOO ;
    String PDBC;
    String PPPO ;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getAASO() {
        return AASO;
    }

    public void setAASO(String AASO) {
        this.AASO = AASO;
    }

    public String getABS_IMG() {
        return ABS_IMG;
    }

    public void setABS_IMG(String ABS_IMG) {
        this.ABS_IMG = ABS_IMG;
    }

    public String getABSC() {
        return ABSC;
    }

    public void setABSC(String ABSC) {
        this.ABSC = ABSC;
    }

    public String getABSE() {
        return ABSE;
    }

    public void setABSE(String ABSE) {
        this.ABSE = ABSE;
    }

    public String getABSO() {
        return ABSO;
    }

    public void setABSO(String ABSO) {
        this.ABSO = ABSO;
    }

    public String getAD() {
        return AD;
    }

    public void setAD(String AD) {
        this.AD = AD;
    }

    public String getANO() {
        return ANO;
    }

    public void setANO(String ANO) {
        this.ANO = ANO;
    }

    public String getAPC() {
        return APC;
    }

    public void setAPC(String APC) {
        this.APC = APC;
    }

    public String getAPE() {
        return APE;
    }

    public void setAPE(String APE) {
        this.APE = APE;
    }

    public String getAPO() {
        return APO;
    }

    public void setAPO(String APO) {
        this.APO = APO;
    }

    public String getASC() {
        return ASC;
    }

    public void setASC(String ASC) {
        this.ASC = ASC;
    }

    public String getASE() {
        return ASE;
    }

    public void setASE(String ASE) {
        this.ASE = ASE;
    }

    public String getASO() {
        return ASO;
    }

    public void setASO(String ASO) {
        this.ASO = ASO;
    }

    public String getASPO() {
        return ASPO;
    }

    public void setASPO(String ASPO) {
        this.ASPO = ASPO;
    }

    public String getBAJDO() {
        return BAJDO;
    }

    public void setBAJDO(String BAJDO) {
        this.BAJDO = BAJDO;
    }

    public String getCIGC() {
        return CIGC;
    }

    public void setCIGC(String CIGC) {
        this.CIGC = CIGC;
    }

    public String getCLN() {
        return CLN;
    }

    public void setCLN(String CLN) {
        this.CLN = CLN;
    }

    public String getCLO() {
        return CLO;
    }

    public void setCLO(String CLO) {
        this.CLO = CLO;
    }

    public String getCRN() {
        return CRN;
    }

    public void setCRN(String CRN) {
        this.CRN = CRN;
    }

    public String getDDO() {
        return DDO;
    }

    public void setDDO(String DDO) {
        this.DDO = DDO;
    }

    public String getDEBEC() {
        return DEBEC;
    }

    public void setDEBEC(String DEBEC) {
        this.DEBEC = DEBEC;
    }

    public String getDEBEE() {
        return DEBEE;
    }

    public void setDEBEE(String DEBEE) {
        this.DEBEE = DEBEE;
    }

    public String getDEBEO() {
        return DEBEO;
    }

    public void setDEBEO(String DEBEO) {
        this.DEBEO = DEBEO;
    }

    public String getDRAP() {
        return DRAP;
    }

    public void setDRAP(String DRAP) {
        this.DRAP = DRAP;
    }

    public String getEFC() {
        return EFC;
    }

    public void setEFC(String EFC) {
        this.EFC = EFC;
    }

    public String geteYears() {
        return eYears;
    }

    public void seteYears(String eYears) {
        this.eYears = eYears;
    }

    public String getEYMD() {
        return EYMD;
    }

    public void setEYMD(String EYMD) {
        this.EYMD = EYMD;
    }

    public String getILSAYMD() {
        return ILSAYMD;
    }

    public void setILSAYMD(String ILSAYMD) {
        this.ILSAYMD = ILSAYMD;
    }

    public String getILSSC() {
        return ILSSC;
    }

    public void setILSSC(String ILSSC) {
        this.ILSSC = ILSSC;
    }

    public String getIMGNAME() {
        return IMGNAME;
    }

    public void setIMGNAME(String IMGNAME) {
        this.IMGNAME = IMGNAME;
    }

    public String getIMGO() {
        return IMGO;
    }

    public void setIMGO(String IMGO) {
        this.IMGO = IMGO;
    }

    public String getIMGTITLE() {
        return IMGTITLE;
    }

    public void setIMGTITLE(String IMGTITLE) {
        this.IMGTITLE = IMGTITLE;
    }

    public String getINC() {
        return INC;
    }

    public void setINC(String INC) {
        this.INC = INC;
    }

    public String getINE() {
        return INE;
    }

    public void setINE(String INE) {
        this.INE = INE;
    }

    public String getINO() {
        return INO;
    }

    public void setINO(String INO) {
        this.INO = INO;
    }

    public String getIPC() {
        return IPC;
    }

    public void setIPC(String IPC) {
        this.IPC = IPC;
    }

    public String getIPCQ() {
        return IPCQ;
    }

    public void setIPCQ(String IPCQ) {
        this.IPCQ = IPCQ;
    }

    public String getISO() {
        return ISO;
    }

    public void setISO(String ISO) {
        this.ISO = ISO;
    }

    public String getLC() {
        return LC;
    }

    public void setLC(String LC) {
        this.LC = LC;
    }

    public String getLinkPath() {
        return linkPath;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }

    public String getLSBCN() {
        return LSBCN;
    }

    public void setLSBCN(String LSBCN) {
        this.LSBCN = LSBCN;
    }

    public String getLSSC() {
        return LSSC;
    }

    public void setLSSC(String LSSC) {
        this.LSSC = LSSC;
    }

    public String getMCLE() {
        return MCLE;
    }

    public void setMCLE(String MCLE) {
        this.MCLE = MCLE;
    }

    public String getPASO() {
        return PASO;
    }

    public void setPASO(String PASO) {
        this.PASO = PASO;
    }

    public String getPCYMD() {
        return PCYMD;
    }

    public void setPCYMD(String PCYMD) {
        this.PCYMD = PCYMD;
    }

    public String getPD() {
        return PD;
    }

    public void setPD(String PD) {
        this.PD = PD;
    }

    public String getPDBC() {
        return PDBC;
    }

    public void setPDBC(String PDBC) {
        this.PDBC = PDBC;
    }

    public String getPDFEXIST() {
        return PDFEXIST;
    }

    public void setPDFEXIST(String PDFEXIST) {
        this.PDFEXIST = PDFEXIST;
    }

    public String getPDT() {
        return PDT;
    }

    public void setPDT(String PDT) {
        this.PDT = PDT;
    }

    public String getPDTC() {
        return PDTC;
    }

    public void setPDTC(String PDTC) {
        this.PDTC = PDTC;
    }

    public String getPERT() {
        return PERT;
    }

    public void setPERT(String PERT) {
        this.PERT = PERT;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getPNO() {
        return PNO;
    }

    public void setPNO(String PNO) {
        this.PNO = PNO;
    }

    public String getPNS() {
        return PNS;
    }

    public void setPNS(String PNS) {
        this.PNS = PNS;
    }

    public String getPPEYMD() {
        return PPEYMD;
    }

    public void setPPEYMD(String PPEYMD) {
        this.PPEYMD = PPEYMD;
    }

    public String getPPOO() {
        return PPOO;
    }

    public void setPPOO(String PPOO) {
        this.PPOO = PPOO;
    }

    public String getPPPO() {
        return PPPO;
    }

    public void setPPPO(String PPPO) {
        this.PPPO = PPPO;
    }

    public String getPPRS() {
        return PPRS;
    }

    public void setPPRS(String PPRS) {
        this.PPRS = PPRS;
    }

    public String getPPRYMD() {
        return PPRYMD;
    }

    public void setPPRYMD(String PPRYMD) {
        this.PPRYMD = PPRYMD;
    }

    public String getPPT() {
        return PPT;
    }

    public void setPPT(String PPT) {
        this.PPT = PPT;
    }

    public String getPPYMD() {
        return PPYMD;
    }

    public void setPPYMD(String PPYMD) {
        this.PPYMD = PPYMD;
    }

    public String getPRN() {
        return PRN;
    }

    public void setPRN(String PRN) {
        this.PRN = PRN;
    }

    public String getPRNO() {
        return PRNO;
    }

    public void setPRNO(String PRNO) {
        this.PRNO = PRNO;
    }

    public String getPT() {
        return PT;
    }

    public void setPT(String PT) {
        this.PT = PT;
    }

    public String getRLYMD() {
        return RLYMD;
    }

    public void setRLYMD(String RLYMD) {
        this.RLYMD = RLYMD;
    }

    public String getSEO() {
        return SEO;
    }

    public void setSEO(String SEO) {
        this.SEO = SEO;
    }

    public String getSFC() {
        return SFC;
    }

    public void setSFC(String SFC) {
        this.SFC = SFC;
    }

    public String getSFPNS() {
        return SFPNS;
    }

    public void setSFPNS(String SFPNS) {
        this.SFPNS = SFPNS;
    }

    public String getTBO() {
        return TBO;
    }

    public void setTBO(String TBO) {
        this.TBO = TBO;
    }

    public String getTFO() {
        return TFO;
    }

    public void setTFO(String TFO) {
        this.TFO = TFO;
    }

    public String getTIC() {
        return TIC;
    }

    public void setTIC(String TIC) {
        this.TIC = TIC;
    }

    public String getTIE() {
        return TIE;
    }

    public void setTIE(String TIE) {
        this.TIE = TIE;
    }

    public String getTIO() {
        return TIO;
    }

    public void setTIO(String TIO) {
        this.TIO = TIO;
    }

    public String getTRO() {
        return TRO;
    }

    public void setTRO(String TRO) {
        this.TRO = TRO;
    }

    public String getTRPO() {
        return TRPO;
    }

    public void setTRPO(String TRPO) {
        this.TRPO = TRPO;
    }

    public String getTT() {
        return TT;
    }

    public void setTT(String TT) {
        this.TT = TT;
    }

    public String getVU() {
        return VU;
    }

    public void setVU(String VU) {
        this.VU = VU;
    }

    public String toString(){
        return "关键词：" + this.getKeyWord() + " 标题 " + this.getTIO();
    }
}
