import java.io.*;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class devAkademi {
    public static int findLargestIndexInArray(int [] arr){
        int maxAt = 0;

        for (int i = 0; i < arr.length; i++) {
            maxAt = arr[i] > arr[maxAt] ? i : maxAt;
        }
        return maxAt;
    }
    public static void main(String args[])throws Exception {
        Scanner scan = new Scanner(System.in);
        String eventTypeArr [] = new String[]{"CLICK", "IMPRESSION"};
        JSONParser parser = new JSONParser();
        ArrayList<String> categoryList = new ArrayList<String>();
        JSONArray tmpArr = (JSONArray) parser.parse(new FileReader("all_data.json"));
        HashMap<Long,Integer> emlakAdClick = new HashMap<Long, Integer>();
        HashMap<Long,Integer> emlakAdIMP = new HashMap<Long, Integer>();
        for (Object obj : tmpArr) {
            /* Extract each JSONObject */
            JSONObject tmpObj = (JSONObject) obj;
            String cat = (String) tmpObj.get("event_category");
            if(!categoryList.contains(cat) && cat != null  && !cat.equals("")){
                categoryList.add(cat);
            }
            //GET AD ID AND # OF IMPRESSION
            /*if(tmpObj.get("event_type").equals("IMPRESSION") && tmpObj.get("ad_id") != null) {
                long adIdImp = tmpObj.get("ad_id").;
                int seqImp = emlakAdIMP.get(adIdImp);
                if (emlakAdIMP.get(adIdImp) == null)
                    emlakAdIMP.put(adIdImp, 1);
                else
                    emlakAdIMP.put(adIdImp, ++seqImp);
            }
            //GET AD ID AND # OF CLICK
            else if(tmpObj.get("event_type").equals("CLICK") && tmpObj.get("ad_id") != null){
                long adIdClick = ((Long) tmpObj.get("ad_id"));
                int seqClick = emlakAdClick.get(adIdClick);
                if (emlakAdClick.get(adIdClick) == null)
                    emlakAdClick.put(adIdClick, 1);
                else
                    emlakAdClick.put(adIdClick, ++seqClick);
            }*/
        }
        int selection = -1;
        int eventType = -1;
        while(selection != 0) {
            int bdateArrEmlak[] = new int[8]; //0-18, 18-25, 25-32, 32-39, 39-46, 46-53, 53-60, +50
            int activeTimeArrEmlak[] = new int[6]; //0-4, 4-8 , 8-12, 12-16, 16-20, 20-24
            int genderArrEmlak[] = new int[2]; // E, K
            int maritalStatusArrEmlak[] = new int[2]; //Evli , Bekar
            System.out.println("Select category to see stats");
            System.out.println("0 ) EXIT");
            for (int i = 0; i < categoryList.size(); i++) {
                System.out.println(i + 1 + " ) " + categoryList.get(i));
            }
            selection = scan.nextInt();
            if (selection != 0 && selection <7){
            System.out.println("Choose event type \n1)CLICK event Logs 2) IMPRESSION event Logs ");
            eventType = scan.nextInt();
                System.out.println("Analaysis for category: " + categoryList.get(selection-1) + " event type : " + eventTypeArr[eventType-1]);
                for (Object obj : tmpArr) {
                    JSONObject tmpObj = (JSONObject) obj;
                    if (tmpObj.get("event_category") != null && tmpObj.get("event_category").equals(categoryList.get(selection-1)) && tmpObj.get("event_type") != null && tmpObj.get("event_type").equals(eventTypeArr[eventType-1])) {
                        //Year analysis
                        if (tmpObj.get("viewer_birt_year") != null) {
                            String byearS = (String) tmpObj.get("viewer_birt_year");
                            int byear = Integer.parseInt(byearS);
                            int age = 2018 - byear;
                            if (age <= 18 && age > 0)
                                bdateArrEmlak[0] = bdateArrEmlak[0] + 1;
                            else if (age > 18 && age <= 25)
                                bdateArrEmlak[1] = bdateArrEmlak[1] + 1;
                            else if (age > 25 && age <= 32)
                                bdateArrEmlak[2] = bdateArrEmlak[2] + 1;
                            else if (age > 32 && age <= 39)
                                bdateArrEmlak[3] = bdateArrEmlak[3] + 1;
                            else if (age > 39 && age <= 46)
                                bdateArrEmlak[4] = bdateArrEmlak[4] + 1;
                            else if (age > 46 && age <= 53)
                                bdateArrEmlak[5] = bdateArrEmlak[5] + 1;
                            else if (age > 53 && age <= 60)
                                bdateArrEmlak[6] = bdateArrEmlak[6] + 1;
                            else if (age > 60)
                                bdateArrEmlak[7] = bdateArrEmlak[7] + 1;
                        }
                        //Time analysis
                        if (tmpObj.get("event_date") != null) {
                            String EpochDate = (String) tmpObj.get("event_date");
                            long epochD = Long.parseLong(EpochDate);
                            Date date = new java.util.Date(epochD * 1000L);
                            if (date.getHours() >= 0 && date.getHours() < 4) {
                                activeTimeArrEmlak[0] = activeTimeArrEmlak[0] + 1;
                            } else if (date.getHours() >= 4 && date.getHours() < 8) {
                                activeTimeArrEmlak[1] = activeTimeArrEmlak[1] + 1;
                            } else if (date.getHours() >= 8 && date.getHours() < 12) {
                                activeTimeArrEmlak[2] = activeTimeArrEmlak[2] + 1;
                            } else if (date.getHours() >= 12 && date.getHours() < 16) {
                                activeTimeArrEmlak[3] = activeTimeArrEmlak[3] + 1;
                            } else if (date.getHours() >= 16 && date.getHours() < 20) {
                                activeTimeArrEmlak[4] = activeTimeArrEmlak[4] + 1;
                            } else if (date.getHours() >= 20 && date.getHours() < 24) {
                                activeTimeArrEmlak[5] = activeTimeArrEmlak[5] + 1;
                            }
                        }
                        //Gender Analysis
                        if (tmpObj.get("viewer_gender") != null) {
                            String gender = (String) tmpObj.get("viewer_gender");
                            if (gender.equals("E"))
                                genderArrEmlak[0] = genderArrEmlak[0] + 1;
                            else if (gender.equals("K"))
                                genderArrEmlak[1] = genderArrEmlak[1] + 1;
                        }
                        //marital Status Analysis
                        if (tmpObj.get("viewer_marital_status") != null) {
                            String maritalStat = (String) tmpObj.get("viewer_marital_status");
                            if (maritalStat.equals("Bekar"))
                                maritalStatusArrEmlak[0] = maritalStatusArrEmlak[0] + 1;
                            if (maritalStat.equals("Evli"))
                                maritalStatusArrEmlak[1] = maritalStatusArrEmlak[1] + 1;
                        }
                    }

                }
                System.out.println("\nAge Analaysis");
                for(int k = 0; k<bdateArrEmlak.length ; k++){
                    if(k ==0)
                    System.out.println("Number of people who are between age of 0 to 18 for " + eventTypeArr[eventType-1] + " event on category " + categoryList.get(selection-1) + ": "+ bdateArrEmlak[k]);
                    else
                        System.out.println("Number of people who are between age of " +(18+(k-1)*7) + " to " + (18+(k*7))+ " for " + eventTypeArr[eventType-1] + " event on category " + categoryList.get(selection-1) + ": "+bdateArrEmlak[k]);
                }
                System.out.println("\nTime analysis");
                for(int k = 0 ; k < activeTimeArrEmlak.length; k++) {
                    System.out.println("Number of people who did "+ eventTypeArr[eventType-1]+" event between "+k*4+ " to " + (k+1)*4+ " o'clock" +" on category " + categoryList.get(selection-1)+ ": "+activeTimeArrEmlak[k]);
                }
                System.out.println("\nGender Analaysis");
                for(int k = 0; k<genderArrEmlak.length; k++) {
                    if(k==0)
                        System.out.println("Number of male user who did " + eventTypeArr[eventType-1]+ " event on category " + categoryList.get(selection-1)+ ": " + genderArrEmlak[k]);
                    else
                        System.out.println("Number of female user who did " + eventTypeArr[eventType-1]+ " event on category " + categoryList.get(selection-1)+ ": " + genderArrEmlak[k]);
                }
                System.out.println("\nMarital Status Analysis");
                for(int k = 0 ; k<maritalStatusArrEmlak.length;k++){
                    if(k == 0)
                        System.out.println("Number of single user who did " + eventTypeArr[eventType-1]+ " event on category " + categoryList.get(selection-1)+ ": " + maritalStatusArrEmlak[k]);
                    else
                        System.out.println("Number of married user who did " + eventTypeArr[eventType-1]+ " event on category " + categoryList.get(selection-1)+ ": " + maritalStatusArrEmlak[k]);
                }
                //Overall Result
                int maxIndexForAge = findLargestIndexInArray(bdateArrEmlak);
                System.out.println();

                int maxIndexForTime = findLargestIndexInArray(activeTimeArrEmlak);
                System.out.println("Between " + maxIndexForTime*4 + " to " + (maxIndexForTime+1)*4 + " o'clock " + eventTypeArr[eventType-1] + " event popular on category " + categoryList.get(selection-1));

                int maxIndexForGender = findLargestIndexInArray(genderArrEmlak);
                if(maxIndexForGender == 0)
                    System.out.println(eventTypeArr[eventType-1] + " event popular among male user on category " + categoryList.get(selection-1));
                else
                    System.out.println(eventTypeArr[eventType-1] + " event popular among male user on category " + categoryList.get(selection-1));

                int maxIndexForMartial = findLargestIndexInArray(maritalStatusArrEmlak);
                if(maxIndexForMartial == 0)
                    System.out.println(eventTypeArr[eventType-1] + " event popular among single user on category " + categoryList.get(selection-1));
                else
                    System.out.println(eventTypeArr[eventType-1] + " event popular among married user on category " + categoryList.get(selection-1));
            }
        }
    }
}
