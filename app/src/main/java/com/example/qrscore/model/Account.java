package com.example.qrscore.model;

import java.util.ArrayList;
import java.util.List;

/** Purpose: This class represents an account in the system.
 * Has score, total scanned, list of scanned QRs and a profile.
 *
 * Outstanding issues:
 * TODO: Finish Purpose
 * TODO: As a player, I want to remove QR codes from my account.
 * TODO: As a player, I want to see my highest and lowest scoring QR codes.
 * TODO: Unit tests
 * TODO: UI--navbar with "add" button to add QR codes.
 */
public class Account {
    private static final String TAG = "ACCOUNT";
    private String userUID;
    private Profile profile;
    private ArrayList<QRCode> qrCodes;
    private Integer score;
    private Integer hiscore;
    private Integer scanned;

    /**
     * Purpose: Empty Constructor for a account.
     */
    public Account() {
        this.userUID = null;
        this.profile = null;
        this.score = null;
        this.hiscore = null;
        this.scanned = null;
        this.qrCodes = null;
    }

    /**
     * Purpose: Constructor for an account instance.
     *
     * @param userUID
     *      The players unique user ID;
     */
    public Account(String userUID) {
        this.userUID = userUID;
        this.profile = new Profile(userUID);
        this.score = 0;
        this.hiscore = 0;
        this.scanned = 0;
        this.qrCodes = new ArrayList<>();
    }

    /**
     * Purpose: Constructor for an account instance.
     *
     * @param userUID
     *      The players unique user ID.
     * @param score
     *      The players running total.
     * @param scanned
     *      The players total scanned QRs.
     */
    public Account(String userUID, Integer score, Integer hiscore, Integer scanned) {
        this.userUID = userUID;
        this.profile = new Profile(userUID);
        this.scanned = scanned;
        this.score = score;
        this.hiscore = hiscore;
        this.qrCodes = new ArrayList<>();
    }

    /**
     * Returns the Account's user ID.
     *
     * @return
     *      user ID as a string.
     */
    public String getUserUID() {
        return userUID;
    }

    /**
     * Purpose: Return the profile in the account.
     * @return
     *      A profile instance.
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Purpose: Sets a profile of a user in the account.
     * @param profile
     *      A profile instance.
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     * Purpose: Return the running total score.
     *
     * @return
     *      Integer representing the total score
     */
    public Integer getScore() {
//        calculateTotalScore();
//        return score;
        return score;
    }

    /**
     * Purpose: Set the score for the account.
     *
     * @param score
     *      Integer representing the accounts score.
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * Purpose: Set the number of QR codes scanned.
     * @param scanned
     *      Integer representing the total QRs scanned.
     */
    public void setScanned(Integer scanned) {
        this.scanned = scanned;
    }

    /**
     * Purpose: Return the number of QR codes scanned.
     * @return
     *      Integer representing the number of QR codes scanned.
     */
    public Integer getScanned() {
        return this.scanned;
    }
//        return qrCodes.size();

    /**
     * Purpose: Set the highest individual QR score for a player.
     * @param hiscore
     *      Integer representing the highest individual QR score.
     */
    public void setHiscore(Integer hiscore) {
        this.hiscore = hiscore;
    }

    /**
     * Purpose: Return the highest individual QR score for a player
     * @return
     *      Integer representing highest individual QR score.
     */
    public Integer getHiscore() {
        return this.hiscore;
    }

    /**
     * Returns the list of QRCodes
     *
     * @return
     *      a List of QR codes.
     */
    public List<QRCode> getQRList() {
        return qrCodes;
    }

    /**
     * Removes a QR code from the list if it exists.
     *
     * @param hash
     *      the hash of the QR code to remove.
     */
    public void removeQR(String hash) {
        for (QRCode qrCode: qrCodes) {
            if (qrCode.getHash() == hash) {
                qrCodes.remove(qrCode);
                return;
            }
        }
    }

    public void setQRCodesList(ArrayList<QRCode> qrCodesArray) {
        this.qrCodes = qrCodesArray;
    }

//    private void calculateTotalScore() {
//        int sum = 0;
//        for (QRCode qrCode: qrCodes) {
//            sum = sum + qrCode.getQRScore();
//        }
//        score = sum;
//    }
}
