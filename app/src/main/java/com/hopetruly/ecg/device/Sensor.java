package com.hopetruly.ecg.device;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import java.util.UUID;

public enum Sensor {
    ACCELEROMETER(ECGUUIDS.SER_ACCELEROMETER_UUID, ECGUUIDS.CHAR_ACCELEROMETER_UUID, ECGUUIDS.DES_ACCELEROMETER_UUID) {
        public DoubleUtils convertACC(byte[] bArr) {
            Integer valueOf = Integer.valueOf(bArr[0]);
            Integer valueOf2 = Integer.valueOf(bArr[1]);
            Integer valueOf3 = Integer.valueOf(bArr[2] * -1);
            return new DoubleUtils(((double) valueOf.intValue()) / 64.0d, ((double) valueOf2.intValue()) / 64.0d, ((double) valueOf3.intValue()) / 64.0d);
        }
    },
    ECG(ECGUUIDS.SER_ECG_UUID, ECGUUIDS.CHAR_ECG_UUID, ECGUUIDS.DES_ECG_UUID) {
        public ConvertECG convertECG(byte[] bArr) {
            float[] fArr = new float[(bArr.length / 2)];
            int i = 0;
            String s = "";
            for (int i2 = 0; i2 < bArr.length; i2 += 2) {
                fArr[i] = (float) ((((short)(bArr[i2 + 1])) << 8) | ((short)(bArr[i2] & 255)));
                s += fArr[i];
                s += ",";
                i++;
            }
            Log.d("Sensor", "the EcgValue is: " + s);
            return new ConvertECG(fArr);
        }
    },
    SIMPLE_KEYS(ECGUUIDS.SERVICE_SIMPLE_KEYS_UUID, ECGUUIDS.CHAR_SIMPLE_KEYS_UUID, (UUID) null) {
        public SimpleKeysStatus convertKeys(byte[] bArr) {
            return SimpleKeysStatus.values()[Integer.valueOf(bArr[0]).intValue() % 4];
        }
    },
    BATTERY(ECGUUIDS.SER_BATTERY_UUID, ECGUUIDS.CHAR_BATTERY_UUID, (UUID) null) {
        public int convertBAT(byte[] bArr) {
            return bArr[0];
        }
    };
    
    public static final byte CALIBRATE_SENSOR_CODE = 2;
    public static final byte DISABLE_SENSOR_CODE = 0;
    public static final byte ENABLE_SENSOR_CODE = 1;
    public static final Sensor[] SENSOR_LIST;
    private final UUID config;
    private final UUID data;
    private byte enableCode;
    private final UUID service;

    static {
        SENSOR_LIST = new Sensor[]{ACCELEROMETER, ECG, SIMPLE_KEYS};
    }

    private Sensor(UUID uuid, UUID uuid2, UUID uuid3) {
        this.service = uuid;
        this.data = uuid2;
        this.config = uuid3;
        this.enableCode = 1;
    }

    private Sensor(UUID uuid, UUID uuid2, UUID uuid3, byte b) {
        this.service = uuid;
        this.data = uuid2;
        this.config = uuid3;
        this.enableCode = b;
    }

    public static Sensor getFromDataUuid(UUID uuid) {
        for (Sensor sensor : values()) {
            if (sensor.getData().equals(uuid)) {
                return sensor;
            }
        }
        throw new RuntimeException("Programmer error, unable to find uuid.");
    }

    public DoubleUtils convertACC(byte[] bArr) {
        throw new UnsupportedOperationException("Programmer error, the individual enum classes are supposed to override this method.");
    }

    public int convertBAT(byte[] bArr) {
        throw new UnsupportedOperationException("Programmer error, the individual enum classes are supposed to override this method.");
    }

    public ConvertECG convertECG(byte[] bArr) {
        throw new UnsupportedOperationException("Programmer error, the individual enum classes are supposed to override this method.");
    }

    public SimpleKeysStatus convertKeys(byte[] bArr) {
        throw new UnsupportedOperationException("Programmer error, the individual enum classes are supposed to override this method.");
    }

    public UUID getConfig() {
        return this.config;
    }

    public UUID getData() {
        return this.data;
    }

    public byte getEnableSensorCode() {
        return this.enableCode;
    }

    public UUID getService() {
        return this.service;
    }

    public void onCharacteristicChanged(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        throw new UnsupportedOperationException("Programmer error, the individual enum classes are supposed to override this method.");
    }
}
